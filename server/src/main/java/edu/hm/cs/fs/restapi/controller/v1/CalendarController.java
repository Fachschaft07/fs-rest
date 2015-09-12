package edu.hm.cs.fs.restapi.controller.v1;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import edu.hm.cs.fs.common.model.Termin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.model.Holiday;
import edu.hm.cs.fs.common.model.Event;
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
    @RequestMapping("/rest/api/1/calendar/termin")
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
    @RequestMapping("/rest/api/1/calendar/holiday")
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
