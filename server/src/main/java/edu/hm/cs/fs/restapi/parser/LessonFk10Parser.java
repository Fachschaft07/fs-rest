package edu.hm.cs.fs.restapi.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.Study;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.common.model.Lesson;
import edu.hm.cs.fs.common.model.simple.SimpleModule;
import edu.hm.cs.fs.common.model.simple.SimplePerson;


public class LessonFk10Parser extends AbstractHtmlParser<Lesson> {

    private static final String URL = "http://w3bw-o.hm.edu/iframe/studieninfo_vorlesungsplan.php";
    private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:28.0) Gecko/20100101 Firefox/28.0";
    private final Group mGroup;

    public LessonFk10Parser(final Group group) {
        super(URL);
        mGroup = group;
    }

    @Override
    public List<Lesson> getAll() {
        final List<Lesson> result = new ArrayList<>();
        try {
            final Connection connect = Jsoup
                    .connect(getUrl())
                    .referrer(getUrl())
                    .userAgent(USER_AGENT)
                    .data("semestergr", getGroupId(mGroup))
                    .data("modul", "")
                    .data("lv", "")
                    .data("dozent", "")
                    .data("kategorie", "Stundenplan")
                    .data("sem", "")
                    .data("Stundenplan anzeigen", "suchen");
            final Document document = connect.post();
            result.addAll(readDoc(document));
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "", e);
        }
        return result;
    }

    @Override
    public List<Lesson> readDoc(final Document document) {
        final List<Lesson> result = new ArrayList<>();

        Day day = null;
        int hour = 0;
        int minute = 0;
        String subject = null;
        String room = null;

        final Element table = document.select("table").get(4);
        final Elements rows = table.getElementsByTag("td");
        for (final Element row : rows) {
            if (row.toString().contains("<h1")) { // i.e. <h1>Montag</h1>
                if (row.toString().contains("Blockveranstaltungen")) {
                    break;
                }
                day = Day.of(row.text().substring(0, 2));
            } else if (row.toString().contains("<h3")) { // i.e. <h3>10:00-11:30 UHR</h3>
                final String time = row.text().substring(0, row.text().indexOf("-"));
                hour = Integer.parseInt(time.split(":")[0]);
                minute = Integer.parseInt(time.split(":")[1]);
            } else if (row.text().matches("[A-Za-z ]+[0-9]+ .*")) { // i.e. B 24 Marketing
                subject = row.text();
            } else if (row.text().matches("[A-Za-z]+[0-9]*")) { // i.e. LE001
                room = row.text();
            } else if (row.text().matches("[^0-9]+")) {
                final String name = row.text().trim();

                final Lesson lesson = new Lesson();
                lesson.setDay(day);
                lesson.setHour(hour);
                lesson.setMinute(minute);

                final SimpleModule module = new SimpleModule();
                module.setId(subject);
                module.setName(subject);
                lesson.setModule(module);

                final SimplePerson person = new SimplePerson();
                person.setId(name);
                person.setName(name);
                lesson.setTeacher(person);

                Set<String> rooms = new TreeSet<String>();
                rooms.add(room);

                lesson.setRoom(room);
                lesson.setRooms(rooms);

                result.add(lesson);
            }
        }

        return result;
    }

    private static String getGroupId(final Group group) {
        Connection conn = Jsoup.connect(URL);
        conn = conn.referrer(URL);
        conn = conn.userAgent(USER_AGENT);
        try {
            final Document doc = conn.get();
            final Elements scripts = doc.select("script");
            final Pattern pattern = Pattern.compile("addOption\\([^\\)]*\\)");
            final Matcher matcher = pattern.matcher(scripts.get(5).toString()); // SS14 = 6; WS1415 = 5
            while (matcher.find()) {
                final String match = matcher.group();
                if (match.contains("WIF ")) {
                    String groupName = match.replaceFirst("addOption\\(\\\"schs[0-9]+\", \"", "");
                    groupName = groupName.replaceFirst("", "");
                    String groupId = groupName;
                    groupName = groupName.replaceFirst("\", \"[0-9]*\"\\)", "");
                    groupId = groupId.replaceFirst("WIF [0-9]*[ ]?[A-Za-z]*\", \"", "");
                    groupId = groupId.replaceFirst("\"\\)", "");

                    final Group foundGroup;
                    final String[] groupSplitter = groupName.split(" ");
                    if (groupSplitter.length > 2 && "M".equalsIgnoreCase(groupSplitter[2])) {
                        foundGroup = Group.of(Study.IN.toString() + groupSplitter[1]);
                    } else {
                        foundGroup = Group.of(Study.IB.toString() + groupSplitter[1] + (groupSplitter.length > 2 ? groupSplitter[2] : ""));
                    }

                    if (group.equals(foundGroup)) {
                        return groupId;
                    }
                }
            }
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "", e);
        }
        return Integer.toString(-1);
    }
}
