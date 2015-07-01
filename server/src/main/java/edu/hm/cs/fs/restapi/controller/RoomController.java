package edu.hm.cs.fs.restapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.Time;
import edu.hm.cs.fs.common.model.Room;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.model.Occupied;
import edu.hm.cs.fs.restapi.parser.OccupiedParser;
import edu.hm.cs.fs.restapi.parser.Parser;

@RestController
public class RoomController {
	@RequestMapping("/rest/api/room")
	public List<Room> search(@RequestParam(value = "day", defaultValue = "mo") String day, @RequestParam(value = "time", defaultValue = "8:15") String time) {
		return new OccupiedParser().parse().parallelStream()
				.filter(occupied -> !occupied.getOccupied().containsKey(Day.of(day)) ||
						occupied.getOccupied().get(Day.of(day)).parallelStream()
								.noneMatch(roomTime -> Time.of(time) == roomTime))
				.collect(Collectors.toList());
	}
}
