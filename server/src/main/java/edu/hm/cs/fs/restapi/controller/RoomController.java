package edu.hm.cs.fs.restapi.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.Time;
import edu.hm.cs.fs.common.model.Room;
import edu.hm.cs.fs.restapi.parser.OccupiedParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fabio
 */
@RestController
public class RoomController {
    private static final int MIN_ROOM_CAPACITY = 4;

    /**
     * @param day
     * @param time
     *
     * @return
     */
    @RequestMapping("/rest/api/room")
    public List<Room> search(@RequestParam(value = "day", defaultValue = "MONDAY") Day day, @RequestParam(value = "time", defaultValue = "LESSON_1") Time time) {
        final List<Time> timesAfter = Stream.of(Time.values())
                .filter(filterTime -> filterTime == time || filterTime.getStart().after(time.getStart()))
                .sorted(Enum::compareTo)
                .collect(Collectors.toList());
        return new OccupiedParser().parse().parallelStream()
                .filter(room -> room.getCapacity() > MIN_ROOM_CAPACITY)
                .map(room -> {
                    Room tmpRoom = new Room();

                    // Convert name from r0009 to R0.009
                    StringBuilder roomNameBuilder = new StringBuilder(room.getName().toUpperCase());
                    roomNameBuilder.insert(2, '.');

                    tmpRoom.setName(roomNameBuilder.toString());
                    tmpRoom.setCapacity(room.getCapacity());

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
                .collect(Collectors.toList());
    }
}
