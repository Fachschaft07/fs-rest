package edu.hm.cs.fs.restapi.parser;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * An abstract parser for xml content.
 *
 * @author Fabio
 */
public abstract class AbstractXmlParser<T> extends AbstractContentParser<T> {
    private final XPath mXPath = XPathFactory.newInstance().newXPath();
    private final String mRootNode;
    private Document xmlDoc;

    /**
     * Creates an abstract parser for xml content.
     *
     * @param url      to parse.
     * @param rootNode of the xml scheme.
     */
    public AbstractXmlParser(final String url, final String rootNode) {
        super(url);
        mRootNode = rootNode;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> read(final String url) {
        final List<T> result = new ArrayList<>();
        xmlDoc = readXml(url);
        if (xmlDoc != null) {
            try {
                // 2014-09-18: BugFix: Wrong count with
                // xmlDoc.getElementByTagName(...)
                // 2014-09-19: Put the getCountByXPah method outside the for-loop to
                // increase speed
                final int countElements = getCountByXPath(mRootNode);
                for (int index = 1; index <= countElements; index++) {
                    final String path = mRootNode + "[" + index + "]";
                    final List<T> value = onCreateItems(path);
                    if (value != null) {
                        result.addAll(value);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                // TODO Log.e(TAG, "", e);
            }
        }
        return result;
    }

    /**
     * Parses the url and convert the content of it to an document.
     *
     * @param url to parse.
     * @return the xml document.
     */
    private Document readXml(final String url) {
        final DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder documentBuilder = null;
        String urlToParse = url;
        try {
            documentBuilder = factory.newDocumentBuilder();
            return documentBuilder.parse(urlToParse);
        } catch (final Exception e) {
            // BugFix: Some URLs removed the ae, ue and oe -> I don't know why?!
            // This is only a little workaround...
            final String baseUrl = urlToParse.substring(0, urlToParse.lastIndexOf("/"));
            final String endPath = urlToParse.substring(urlToParse.lastIndexOf("/"));
            if (urlToParse.contains("ae") || urlToParse.contains("ue") || urlToParse.contains("oe")) {
                urlToParse = baseUrl + endPath.replaceAll("ae", "").replaceAll("ue", "").replaceAll("oe", "");
                // TODO Log.w(getClass().getSimpleName(), "Failed to connect to side -> Now try: " + urlToParse, e);
                readXml(urlToParse);
            } else {
                // TODO Log.w(getClass().getSimpleName(), "Failed to connect to side: " + url, e);
            }
        }
        return null;
    }

    /**
     * Create the next items at the specified index.
     *
     * @param rootPath of the items.
     * @return the items.
     * @throws IOException 
     * @throws MalformedURLException 
     */
    public abstract List<T> onCreateItems(String rootPath) throws XPathExpressionException, MalformedURLException, IOException;

    /**
     * Find a element by using the {@link XPath}.
     *
     * @param xPath      for searching.
     * @param name       of the type.
     * @param returnType the type of the object which will be returned.
     * @return the found value.
     */
    public <X> X findByXPath(final String xPath, final QName name, final Class<X> returnType)
            throws XPathExpressionException {
        return returnType.cast(mXPath.evaluate(xPath, xmlDoc, name));
    }

    /**
     * Get the count of elements of a name.
     *
     * @param xPath to the element.
     * @return the count.
     */
    public int getCountByXPath(final String xPath) throws XPathExpressionException {
        return findByXPath(xPath, XPathConstants.NODESET, NodeList.class).getLength();
    }
}
