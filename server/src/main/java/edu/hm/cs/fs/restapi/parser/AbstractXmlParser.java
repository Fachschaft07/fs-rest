package edu.hm.cs.fs.restapi.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * An abstract parser for xml content.
 *
 * @author Fabio
 */
public abstract class AbstractXmlParser<T> extends AbstractContentParser<T> {
    private final String mRootNode;
    private Document xmlDoc;

    /**
     * Creates an abstract parser for xml content.
     *
     * @param url      to getAll.
     * @param rootNode of the xml scheme.
     */
    public AbstractXmlParser(final String url, final String rootNode) {
        super(url);
        mRootNode = rootNode;
    }

    @Override
    public List<T> getAll() throws Exception {
        final List<T> result;
        xmlDoc = readXml(getUrl());
        if (xmlDoc != null) {
            // Performance increased by >150% with streams!!!
            result = getXPathStream(getRootNode())
                    .map(path -> {
                        try {
                            return onCreateItems(path);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .flatMap(Collection::parallelStream)
                    .collect(Collectors.toList());
        } else {
            result = new ArrayList<>();
        }
        return result;
    }

    /**
     * Parses the url and convert the content of it to an document.
     *
     * @param url to getAll.
     * @return the xml document.
     */
    private Document readXml(final String url) throws IOException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        String urlToParse = url;
        try {
            final DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            return documentBuilder.parse(urlToParse);
        } catch (final Exception e) {
            // BugFix: Some URLs removed the ae, ue and oe -> I don't know why?!
            // This is only a little workaround...
            final String baseUrl = urlToParse.substring(0, urlToParse.lastIndexOf("/"));
            final String endPath = urlToParse.substring(urlToParse.lastIndexOf("/"));
            if (urlToParse.contains("ae") || urlToParse.contains("ue") || urlToParse.contains("oe")) {
                urlToParse = baseUrl + endPath.replaceAll("ae", "").replaceAll("ue", "").replaceAll("oe", "");
                readXml(urlToParse);
            } else {
                throw new IOException(e);
            }
        }
        return null;
    }

    /**
     * Create the next items at the specified index.
     *
     * @param rootPath of the items.
     * @return the items.
     * @throws Exception
     */
    public abstract List<T> onCreateItems(String rootPath) throws Exception;

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
        return returnType.cast(XPathFactory.newInstance().newXPath().evaluate(xPath, xmlDoc, name));
    }

    /**
     * Streams all the elements from the specified xpath. The elements are the indexed path.
     *
     * @param path to the xpath elements.
     * @return the stream.
     * @throws XPathExpressionException
     */
    public Stream<String> getXPathStream(final String path) throws XPathExpressionException {
        return IntStream.rangeClosed(1, findByXPath(path, XPathConstants.NODESET, NodeList.class).getLength())
                .parallel()
                .mapToObj(index -> path + "[" + index + "]");
    }

    /**
     * Get the root node of the xml content.
     *
     * @return the root node.
     */
    public String getRootNode() {
        return mRootNode;
    }
}
