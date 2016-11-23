package edu.hm.cs.fs.restapi.controller;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * @param location
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "getMeals")
    @RequestMapping(method = RequestMethod.GET, value = "/meal", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "location", value = "Location of mensa or stu cafe", required = true, dataType = "string", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 101, message = "java.lang.RuntimeException"),
            @ApiResponse(code = 103, message = "org.springframework.web.bind.MissingServletRequestParameterException"),
            @ApiResponse(code = 107, message = "java.lang.IllegalStateException"),
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 113, message = "javax.xml.xpath.XPathExpressionException"),
            @ApiResponse(code = 200, message = "Success")
    })
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
