package edu.hm.cs.fs.restapi.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
     */
    @RequestMapping("/rest/api/1/calendar/termin")
    public List<Termin> getTermins() {
        return new TerminParser().parse().parallelStream()
                .filter(termin -> !termin.getSubject().endsWith("erster Tag") &&
                        !termin.getSubject().endsWith("letzter Tag"))
                .sorted((termin1, termin2) -> termin1.getDate().compareTo(termin2.getDate()))
                .collect(Collectors.toList());
    }

    /**
     * @return
     */
    @RequestMapping("/rest/api/1/calendar/holiday")
    public List<Holiday> getHolidays() {
        final List<Termin> termins = new TerminParser().parse();
        return termins.parallelStream()
                .filter(termin -> termin.getSubject().endsWith("erster Tag"))
                .map(termin -> {
                    Holiday holiday = new Holiday();
                    holiday.setName(getName(termin.getSubject(), termin.getDate()));
                    holiday.setStart(termin.getDate());
                    termins.parallelStream()
                            .filter(termin1 -> termin1.getSubject().endsWith("letzter Tag"))
                            .filter(termin1 -> getName(termin1.getSubject(), termin1.getDate())
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
