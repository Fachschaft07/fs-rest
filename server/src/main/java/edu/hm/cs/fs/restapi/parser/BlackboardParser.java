package edu.hm.cs.fs.restapi.parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.jsoup.helper.StringUtil;

import edu.hm.cs.fs.common.model.BlackboardEntry;

/**
 * The news which are shown at the black board. (Url: <a
 * href="http://fi.cs.hm.edu/fi/rest/public/news"
 * >http://fi.cs.hm.edu/fi/rest/public/news</a>)
 *
 * @author Fabio
 *
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
	public BlackboardEntry onCreateItem(final String rootPath) throws XPathExpressionException {
		String mId;
		String mAuthor;
		String mSubject;
		String mText;
		List<String> mTeacherList = new ArrayList<>();
		List<String> mGroupList = new ArrayList<>();
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
				mTeacherList.add(new String(teacherName));
			}
		}

		final int groupCount = getCountByXPath(rootPath + "/group");
		for (int groupIndex = 1; groupIndex <= groupCount; groupIndex++) {
			final String groupName = findByXPath(rootPath + "/group["
					+ groupIndex + "]/text()", XPathConstants.STRING, String.class);
			if (!StringUtil.isBlank(groupName)) {
				mGroupList.add(new String(groupName));
			}
		}
		
		BlackboardEntry blackboardEntry = new BlackboardEntry();
		blackboardEntry.setId(mId);
		blackboardEntry.setAuthor(mAuthor);
		blackboardEntry.setSubject(mSubject);
		blackboardEntry.setText(mText);
		blackboardEntry.setGroups(mGroupList);
		blackboardEntry.setTeachers(mTeacherList);
		blackboardEntry.setPublish(mPublish);
		blackboardEntry.setExpire(mExpire);
		blackboardEntry.setUrl(mUrl);

		return blackboardEntry;
	}
}
