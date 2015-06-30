package edu.hm.cs.fs.restapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.model.Occupied;
import edu.hm.cs.fs.restapi.parser.OccupiedParser;
import edu.hm.cs.fs.restapi.parser.Parser;

@RestController
public class RoomsController {

	private final Parser<Occupied> parser;

	public RoomsController() {
		parser = new OccupiedParser();
	}

	@RequestMapping("/rest/api/rooms")
	public List<Occupied> getRooms(@RequestParam(value = "day", defaultValue = "mo") String day, @RequestParam(value = "time", defaultValue = "8:15") String time) {
		return parser.parse().stream()
				.filter(room -> room.getDay().equals(day))
				.filter(room -> !room.getTime().equals(time))
				.collect(Collectors.toList());
	}
}
