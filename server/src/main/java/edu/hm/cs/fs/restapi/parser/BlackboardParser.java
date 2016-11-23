package edu.hm.cs.fs.restapi.parser;

import com.google.common.base.Strings;
import edu.hm.cs.fs.common.model.BlackboardEntry;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.common.model.simple.SimplePerson;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The news which are shown at the black board. (Url:
 * <a href="http://fi.cs.hm.edu/fi/rest/public/news" >http://fi.cs.hm.edu/fi/rest/public/news</a>)
 *
 * @author Fabio
 */
public class BlackboardParser extends AbstractXmlParser<BlackboardEntry>
        implements ByIdParser<BlackboardEntry> {
    private final static Logger LOG = Logger.getLogger(BlackboardParser.class);
    private static final String URL = "http://fi.cs.hm.edu/fi/rest/public/news.xml";
    private static final String ROOT_NODE = "/newslist/news";

    private final ByIdParser<Person> personParser;

    public BlackboardParser(ByIdParser<Person> personParser) {
        super(URL, ROOT_NODE);
        this.personParser = personParser;
    }

    @Override
    public List<BlackboardEntry> onCreateItems(final String rootPath) {
        final List<BlackboardEntry> result = new ArrayList<>();

        // Parse Elements...
        final Optional<String> id = findString(rootPath + "/id/text()");
        final Optional<String> author = findString(rootPath + "/author/text()");
        final Optional<String> subject = findString(rootPath + "/subject/text()");
        final Optional<String> text = findString(rootPath + "/text/text()");
        final Optional<String> publishDate = findString(rootPath + "/publish/text()");
        final Optional<String> url = findString(rootPath + "/url/text()");
        final Date publish = publishDate.map(date -> {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(date);
            } catch (ParseException e) {
                LOG.warn(e);
                return null;
            }
        }).orElse(new Date());
        final List<SimplePerson> teacherList = getXPathStream(rootPath + "/teacher")
                .parallel()
                .map(path -> findString(path + "/text()"))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(personParser::getById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(SimplePerson::new)
                .collect(Collectors.toList());
        final List<Group> groupList = getXPathStream(rootPath + "/group")
                .parallel()
                .map(path -> findString(path + "/text()"))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(group -> !Strings.isNullOrEmpty(group))
                .map(Group::of)
                .collect(Collectors.toList());

        if (id.isPresent() && author.isPresent() && subject.isPresent() && text.isPresent() && publishDate.isPresent() && url.isPresent()) {
            BlackboardEntry blackboardEntry = new BlackboardEntry();
            blackboardEntry.setId(id.get());
            blackboardEntry.setSubject(subject.get());
            blackboardEntry.setText(StringUtils.replace(HtmlUtils.htmlEscape(text.get()), "#", "<br/>"));
            blackboardEntry.setGroups(groupList);
            blackboardEntry.setTeachers(teacherList);
            blackboardEntry.setPublish(publish);
            blackboardEntry.setUrl(url.get());
            personParser.getById(author.get()).map(SimplePerson::new).ifPresent(blackboardEntry::setAuthor);

            result.add(blackboardEntry);
        }

        return result;
    }

    @Override
    public Optional<BlackboardEntry> getById(String itemId) {
        return getAll().parallelStream().filter(item -> itemId.equalsIgnoreCase(item.getId()))
                .findAny();
    }
}
