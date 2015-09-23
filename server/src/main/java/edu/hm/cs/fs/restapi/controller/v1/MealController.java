package edu.hm.cs.fs.restapi.controller.v1;

import java.util.Calendar;
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
     * @throws Exception
     */
    @RequestMapping("/rest/api/1/meal")
    public List<Meal> getMeals(@RequestParam(value = "location") StudentWorkMunich location) throws Exception {
        return new MealParser(location).getAll()
                .parallelStream()
                .filter(meal -> {
                    Calendar today = Calendar.getInstance();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(meal.getDate());

                    // Is the meal today or in the future?
                    return cal.after(today)
                            || today.get(Calendar.YEAR) == cal.get(Calendar.YEAR)
                            && today.get(Calendar.MONTH) == cal.get(Calendar.MONTH)
                            && today.get(Calendar.DATE) == cal.get(Calendar.DATE);
                })
                .sorted((meal1, meal2) -> meal1.getDate().compareTo(meal2.getDate()))
                .collect(Collectors.toList());
    }
}
