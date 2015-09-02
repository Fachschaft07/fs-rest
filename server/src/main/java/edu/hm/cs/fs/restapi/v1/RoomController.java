package edu.hm.cs.fs.restapi.v1;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.Time;
import edu.hm.cs.fs.common.model.Room;
import edu.hm.cs.fs.common.model.SimpleRoom;
import edu.hm.cs.fs.common.model.util.ModelUtil;
import edu.hm.cs.fs.restapi.parser.cache.CachedOccupiedParser;

/**
 * @author Fabio
 */
@RestController
public class RoomController {
    private static final int MIN_ROOM_CAPACITY = 4;

    /**
     *
     * @param day
     * @param hour
     * @param minute
     * @return
     */
    @RequestMapping("/rest/api/1/room")
    public List<SimpleRoom> search(@RequestParam(value = "day", defaultValue = "MONDAY") Day day,
                             @RequestParam(value = "hour", defaultValue = "8") int hour,
                             @RequestParam(value = "minute", defaultValue = "0") int minute) {
        final List<Time> timesAfter = Stream.of(Time.values())
                .filter(filterTime -> filterTime.getStart().get(Calendar.HOUR_OF_DAY) >= hour ||
                        filterTime.getStart().get(Calendar.HOUR_OF_DAY) == hour &&
                                filterTime.getStart().get(Calendar.MINUTE) >= minute)
                .sorted(Enum::compareTo)
                .collect(Collectors.toList());
        return ModelUtil.convert(new CachedOccupiedParser().parse().parallelStream()
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
                .collect(Collectors.toList()), SimpleRoom.class);
    }
}
