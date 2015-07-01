package edu.hm.cs.fs.restapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.constant.StudentWorkMunich;
import edu.hm.cs.fs.common.model.Meal;
import edu.hm.cs.fs.restapi.parser.MealParser;

@RestController
public class MealController {
    @RequestMapping("/rest/api/meal")
    public List<Meal> meal(@RequestParam(value="location", defaultValue = "") String location) {
        return new MealParser(StudentWorkMunich.valueOf(location.toUpperCase())).parse();
    }
}
