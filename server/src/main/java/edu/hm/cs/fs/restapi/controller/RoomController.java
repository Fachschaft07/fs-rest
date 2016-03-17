package edu.hm.cs.fs.restapi.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.RoomType;
import edu.hm.cs.fs.common.constant.Time;
import edu.hm.cs.fs.common.model.Room;
import edu.hm.cs.fs.common.model.simple.SimpleRoom;
import edu.hm.cs.fs.restapi.parser.cache.CachedOccupiedParser;

/**
 * @author Fabio, Luca
 */
@RestController
public class RoomController {
    private static final int MIN_ROOM_CAPACITY = 4;

    private static List<String> excludedRooms = new ArrayList<String>() {
        {
            add("R3.013");
            add("R3.014");
            add("R3.015");
            add("R3.016");
            add("R3.017");
        }
    };

    /**
     * @param day
     * @param hour
     * @param minute
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "getFreeRoomsByDateTime")
    @RequestMapping(method = RequestMethod.GET, value = "/rest/api/1/room", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "Type of room", required = false, dataType = "string", paramType = "query", defaultValue = "ALL"),
            @ApiImplicitParam(name = "day", value = "Weekday", required = false, dataType = "string", paramType = "query", defaultValue = "MONDAY"),
            @ApiImplicitParam(name = "hour", value = "Hours", required = false, dataType = "int", paramType = "query", defaultValue = "8"),
            @ApiImplicitParam(name = "minute", value = "Minutes", required = false, dataType = "int", paramType = "query", defaultValue = "0")
    })
    @ApiResponses({
            @ApiResponse(code = 101, message = "java.lang.RuntimeException"),
            @ApiResponse(code = 103, message = "org.springframework.web.bind.MissingServletRequestParameterException"),
            @ApiResponse(code = 107, message = "java.lang.IllegalStateException"),
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 113, message = "javax.xml.xpath.XPathExpressionException"),
            @ApiResponse(code = 200, message = "Success")
    })
    public List<SimpleRoom> getRoomByDateTime(
            @RequestParam(value = "type", defaultValue = "ALL") RoomType type,
            @RequestParam(value = "day", defaultValue = "MONDAY") Day day,
            @RequestParam(value = "hour", defaultValue = "8") int hour,
            @RequestParam(value = "minute", defaultValue = "0") int minute) throws Exception {
        final List<Time> timesAfter = Stream.of(Time.values())
                .filter(filterTime -> filterTime.getStart().get(Calendar.HOUR_OF_DAY) >= hour
                        || filterTime.getStart().get(Calendar.HOUR_OF_DAY) == hour
                        && filterTime.getStart().get(Calendar.MINUTE) >= minute)
                .sorted(Enum::compareTo).collect(Collectors.toList());

        return CachedOccupiedParser.getInstance().getAll().parallelStream()
                .filter(room -> !excludedRooms.contains(room.getName()))
                .filter(room -> type == RoomType.ALL || type == room.getRoomType())
                .filter(room -> room.getCapacity() > MIN_ROOM_CAPACITY).map(room -> {
                    Room tmpRoom = new Room();

                    tmpRoom.setName(room.getName());
                    tmpRoom.setCapacity(room.getCapacity());
                    tmpRoom.setRoomType(room.getRoomType());

                    // Does the room is occupied at any time this day?
                    if (room.getOccupied().containsKey(day)) {
                        // YES!
                        // Get times when the room is free
                        Optional<Time> freeTime = Optional.empty();
                        for (final Time possibleFreeTime : timesAfter) {
                            if (room.getOccupied().get(day).parallelStream()
                                    .noneMatch(occupiedTime -> occupiedTime == possibleFreeTime)) {
                                freeTime = Optional.of(possibleFreeTime);
                            } else {
                                break;
                            }
                        }
                        // Empty? -> Room is not free today!
                        if (!freeTime.isPresent()) {
                            return null;
                        }
                        // Extract the latest free time
                        tmpRoom.setFreeUntilEnd(freeTime.get());
                    } else {
                        // NO!
                        tmpRoom.setFreeUntilEnd(Time.LESSON_8);
                    }
                    return tmpRoom;
                })
                .filter(room -> room != null)
                .map(SimpleRoom::new)
                .sorted((r1, r2) -> r1.getName().compareTo(r2.getName()))
                .collect(Collectors.toList());
    }
}
