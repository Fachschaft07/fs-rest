package edu.hm.cs.fs.restapi.v1;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.constant.StudentWorkMunich;
import edu.hm.cs.fs.common.model.Meal;
import edu.hm.cs.fs.restapi.parser.MealParser;

/**
 * @author Fabio
 */
@RestController
public class MealController {
    /**
     *
     * @param location
     * @return
     */
    @RequestMapping("/rest/api/1/meal")
    public List<Meal> meal(@RequestParam(value="location") StudentWorkMunich location) {
        return new MealParser(location).parse()
                .stream()
                .filter(meal -> new Date().after(meal.getDate()))
                .collect(Collectors.toList());
    }
}
