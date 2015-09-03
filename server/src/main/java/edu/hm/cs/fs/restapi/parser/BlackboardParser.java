package edu.hm.cs.fs.restapi.parser;

import org.jsoup.helper.StringUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import edu.hm.cs.fs.common.model.BlackboardEntry;
import edu.hm.cs.fs.common.model.Group;
import edu.hm.cs.fs.common.model.SimplePerson;
import edu.hm.cs.fs.restapi.parser.cache.CachedPersonParser;

/**
 * The news which are shown at the black board. (Url: <a href="http://fi.cs.hm.edu/fi/rest/public/news"
 * >http://fi.cs.hm.edu/fi/rest/public/news</a>)
 *
 * @author Fabio
 */
public class BlackboardParser extends AbstractXmlParser<BlackboardEntry> {
    private static final String URL = "http://fi.cs.hm.edu/fi/rest/public/news.xml";
    private static final String ROOT_NODE = "/newslist/news";

    private static final DateFormat DATE_PARSER = new SimpleDateFormat(
            "yyyy-MM-dd");

    public BlackboardParser() {
        super(URL, ROOT_NODE);
    }

    @Override
    public List<BlackboardEntry> onCreateItems(final String rootPath) throws XPathExpressionException, MalformedURLException, IOException {
        String mId;
        String mAuthor;
        String mSubject;
        String mText;
        List<SimplePerson> mTeacherList = new ArrayList<>();
        List<Group> mGroupList = new ArrayList<>();
        Date mPublish = null;
        Date mExpire = null;
        String mUrl;

        // Parse Elements...
        mId = findByXPath(rootPath + "/id/text()",
                XPathConstants.STRING, String.class);
        mAuthor = findByXPath(rootPath + "/author/text()",
                XPathConstants.STRING, String.class);
        mSubject = findByXPath(rootPath + "/subject/text()",
                XPathConstants.STRING, String.class);
        mText = findByXPath(rootPath + "/text/text()",
                XPathConstants.STRING, String.class);

        final String publishDate = findByXPath(
                rootPath + "/publish/text()", XPathConstants.STRING, String.class);
        if (!StringUtil.isBlank(publishDate)) {
            try {
                mPublish = DATE_PARSER.parse(publishDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        final String expireDate = findByXPath(rootPath + "/expire/text()",
                XPathConstants.STRING, String.class);
        if (!StringUtil.isBlank(expireDate)) {
            try {
                mExpire = DATE_PARSER.parse(expireDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        mUrl = findByXPath(rootPath + "/url/text()", XPathConstants.STRING, String.class);

        final int teacherCount = getCountByXPath(rootPath + "/teacher");
        for (int teacherIndex = 1; teacherIndex <= teacherCount; teacherIndex++) {
            final String teacherName = findByXPath(rootPath + "/teacher["
                    + teacherIndex + "]/text()", XPathConstants.STRING, String.class);
            if (!StringUtil.isBlank(teacherName)) {
                new CachedPersonParser().findByIdSimple(teacherName).ifPresent(mTeacherList::add);
            }
        }

        final int groupCount = getCountByXPath(rootPath + "/group");
        for (int groupIndex = 1; groupIndex <= groupCount; groupIndex++) {
            final String groupName = findByXPath(rootPath + "/group["
                    + groupIndex + "]/text()", XPathConstants.STRING, String.class);
            if (!StringUtil.isBlank(groupName)) {
                mGroupList.add(Group.of(groupName));
            }
        }

        BlackboardEntry blackboardEntry = new BlackboardEntry();
        blackboardEntry.setId(mId);
        blackboardEntry.setSubject(mSubject);
        blackboardEntry.setText(mText);
        blackboardEntry.setGroups(mGroupList);
        blackboardEntry.setTeachers(mTeacherList);
        blackboardEntry.setPublish(mPublish);
        //blackboardEntry.setExpire(mExpire);
        blackboardEntry.setUrl(mUrl);

        if (!StringUtil.isBlank(mAuthor)) {
            new CachedPersonParser().findByIdSimple(mAuthor).ifPresent(blackboardEntry::setAuthor);
        }

        return Collections.singletonList(blackboardEntry);
    }
}
