package edu.hm.cs.fs.restapi.parser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import com.google.common.base.Strings;

import edu.hm.cs.fs.common.model.BlackboardEntry;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.common.model.Person;
import edu.hm.cs.fs.common.model.simple.SimplePerson;

/**
 * The news which are shown at the black board. (Url:
 * <a href="http://fi.cs.hm.edu/fi/rest/public/news" >http://fi.cs.hm.edu/fi/rest/public/news</a>)
 *
 * @author Fabio
 */
public class BlackboardParser extends AbstractXmlParser<BlackboardEntry>
    implements ByIdParser<BlackboardEntry> {
  private static final String URL = "http://fi.cs.hm.edu/fi/rest/public/news.xml";
  private static final String ROOT_NODE = "/newslist/news";

  private final Logger logger = Logger.getLogger(getClass());

  private final ByIdParser<Person> personParser;

  public BlackboardParser(ByIdParser<Person> personParser) {
    super(URL, ROOT_NODE);
    this.personParser = personParser;
  }

  @Override
  public List<BlackboardEntry> onCreateItems(final String rootPath) throws Exception {
    String mId;
    String mAuthor;
    String mSubject;
    String mText;
    Date mPublish = null;
    String mUrl;

    // Parse Elements...
    mId = findByXPath(rootPath + "/id/text()", XPathConstants.STRING, String.class);
    mAuthor = findByXPath(rootPath + "/author/text()", XPathConstants.STRING, String.class);
    mSubject = findByXPath(rootPath + "/subject/text()", XPathConstants.STRING, String.class);
    mText = findByXPath(rootPath + "/text/text()", XPathConstants.STRING, String.class);

    mText = HtmlUtils.htmlEscape(mText);
    mText = StringUtils.replace(mText, "#", "<br/>");
    
    final String publishDate =
        findByXPath(rootPath + "/publish/text()", XPathConstants.STRING, String.class);
    if (!StringUtil.isBlank(publishDate)) {
      try {
        mPublish = new SimpleDateFormat("yyyy-MM-dd").parse(publishDate);
      } catch (ParseException e) {
        mPublish = new Date();
        logger.error(e.getMessage(), e);
      }
    }

    mUrl = findByXPath(rootPath + "/url/text()", XPathConstants.STRING, String.class);

    final List<SimplePerson> mTeacherList = getXPathStream(rootPath + "/teacher").map(path -> {
      try {
        return findByXPath(path + "/text()", XPathConstants.STRING, String.class);
      } catch (XPathExpressionException e) {
        throw new RuntimeException(e);
      }
    }).filter(person -> !Strings.isNullOrEmpty(person)).map(person -> {
      try {
        return personParser.getById(person);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }).filter(Optional::isPresent).map(Optional::get).map(SimplePerson::new)
        .collect(Collectors.toList());

    final List<Group> mGroupList = getXPathStream(rootPath + "/group").map(path -> {
      try {
        return findByXPath(path + "/text()", XPathConstants.STRING, String.class);
      } catch (XPathExpressionException e) {
        throw new RuntimeException(e);
      }
    }).filter(group -> !Strings.isNullOrEmpty(group)).map(Group::of).collect(Collectors.toList());

    BlackboardEntry blackboardEntry = new BlackboardEntry();
    blackboardEntry.setId(mId);
    blackboardEntry.setSubject(mSubject);
    blackboardEntry.setText(mText);
    blackboardEntry.setGroups(mGroupList);
    blackboardEntry.setTeachers(mTeacherList);
    blackboardEntry.setPublish(mPublish);
    blackboardEntry.setUrl(mUrl);
    personParser.getById(mAuthor).map(SimplePerson::new).ifPresent(blackboardEntry::setAuthor);

    return Collections.singletonList(blackboardEntry);
  }

  @Override
  public Optional<BlackboardEntry> getById(String itemId) throws Exception {
    try {
      return getAll().parallelStream().filter(item -> itemId.equalsIgnoreCase(item.getId()))
          .findAny();
    } catch (IOException e) {
      return Optional.empty();
    }
  }
}
