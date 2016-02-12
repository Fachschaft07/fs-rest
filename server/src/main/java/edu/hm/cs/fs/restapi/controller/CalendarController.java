package edu.hm.cs.fs.restapi.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.model.Holiday;
import edu.hm.cs.fs.common.model.Termin;
import edu.hm.cs.fs.restapi.parser.TerminParser;

/**
 * @author Fabio
 */
@RestController
public class CalendarController {
    /**
     * @return
     * @throws IOException 
     * @throws Exception
     */
    @ApiOperation(value = "getTermins")
    @RequestMapping(method = RequestMethod.GET, value = "/rest/api/1/calendar/termin", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 101, message = "java.lang.RuntimeException"),
            @ApiResponse(code = 103, message = "org.springframework.web.bind.MissingServletRequestParameterException"),
            @ApiResponse(code = 107, message = "java.lang.IllegalStateException"),
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 113, message = "javax.xml.xpath.XPathExpressionException"),
            @ApiResponse(code = 200, message = "Success")
    })
    public List<Termin> getTermins() throws Exception {
        return new TerminParser().getAll().parallelStream()
                .filter(termin -> !termin.getTitle().endsWith("erster Tag") &&
                        !termin.getTitle().endsWith("letzter Tag"))
                .sorted((termin1, termin2) -> termin1.getDate().compareTo(termin2.getDate()))
                .collect(Collectors.toList());
    }

    /**
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "getHolidays")
    @RequestMapping(method = RequestMethod.GET, value = "/rest/api/1/calendar/holiday", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 101, message = "java.lang.RuntimeException"),
            @ApiResponse(code = 103, message = "org.springframework.web.bind.MissingServletRequestParameterException"),
            @ApiResponse(code = 107, message = "java.lang.IllegalStateException"),
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 113, message = "javax.xml.xpath.XPathExpressionException"),
            @ApiResponse(code = 200, message = "Success")
    })
    public List<Holiday> getHolidays() throws Exception {
        final List<Termin> events = new TerminParser().getAll();
        return events.parallelStream()
                .filter(termin -> termin.getTitle().endsWith("erster Tag"))
                .map(termin -> {
                    Holiday holiday = new Holiday();
                    holiday.setName(getName(termin.getTitle(), termin.getDate()));
                    holiday.setStart(termin.getDate());
                    events.parallelStream()
                            .filter(termin1 -> termin1.getTitle().endsWith("letzter Tag"))
                            .filter(termin1 -> getName(termin1.getTitle(), termin1.getDate())
                                    .equalsIgnoreCase(holiday.getName()))
                            .findFirst()
                            .ifPresent(termin1 -> holiday.setEnd(termin1.getDate()));
                    return holiday;
                })
                .sorted((holiday1, holiday2) -> holiday1.getStart().compareTo(holiday2.getStart()))
                .collect(Collectors.toList());
    }

    /**
     * @param subject
     * @param date
     *
     * @return
     */
    private String getName(String subject, Date date) {
        String tmpName = subject.substring(0, subject.indexOf(",")).trim();
        if (tmpName.startsWith("Weihnachtsferien") && subject.endsWith("erster Tag")) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            tmpName += "/" + (cal.get(Calendar.YEAR) + 1);
        } else if (tmpName.startsWith("Weihnachtsferien") && subject.endsWith("letzter Tag")) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            tmpName = tmpName.replaceAll("[0-9]+", "") + (cal.get(Calendar.YEAR) - 1) + "/" + cal.get(Calendar.YEAR);
        }
        return tmpName;
    }

}
