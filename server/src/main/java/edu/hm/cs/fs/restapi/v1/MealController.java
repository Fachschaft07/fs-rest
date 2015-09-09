package edu.hm.cs.fs.restapi.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.xpath.XPathExpressionException;

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
     * @throws IOException 
     * @throws XPathExpressionException
     */
    @RequestMapping("/rest/api/1/meal")
    public List<Meal> getMeals(@RequestParam(value = "location") StudentWorkMunich location) throws XPathExpressionException, IOException {
        return new MealParser(location).parse()
                .stream()
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
                .collect(Collectors.toList());
    }
}
