package edu.hm.cs.fs.restapi.parser;

import com.google.common.base.Stopwatch;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * An abstract parser for xml content.
 *
 * @author Fabio
 */
public abstract class AbstractXmlParser<T> extends AbstractContentParser<T> {
    private final static Logger LOG = Logger.getLogger(AbstractXmlParser.class);
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
    public List<T> getAll() {
        final List<T> result = new ArrayList<>();
        try {
            final Stopwatch stopwatch = Stopwatch.createStarted();

            xmlDoc = readXml(getUrl());
            if (xmlDoc != null) {
                // Performance increased by >150% with streams!!!
                result.addAll(getXPathStream(getRootNode())
                        .map(this::onCreateItems)
                        .flatMap(Collection::parallelStream)
                        .collect(Collectors.toList()));
            }

            stopwatch.stop();
            LOG.info("Requesting and parsing finished in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms on " + getUrl());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e);
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
     */
    public abstract List<T> onCreateItems(String rootPath);

    /**
     * Find a text element by using the {@link XPath}.
     *
     * @param xPath for searching.
     * @return the found text element or nothing.
     * @see #findByXPath(String, QName, Class)
     */
    public Optional<String> findString(final String xPath) {
        return findByXPath(xPath, XPathConstants.STRING, String.class);
    }

    /**
     * Find a number element by using the {@link XPath}.
     *
     * @param xPath for searching.
     * @return the found number element or nothing.
     * @see #findByXPath(String, QName, Class)
     */
    public Optional<Double> findNumber(final String xPath) {
        return findByXPath(xPath, XPathConstants.NUMBER, Double.class);
    }

    /**
     * Find a boolean element by using the {@link XPath}.
     *
     * @param xPath for searching.
     * @return the found boolean element or nothing.
     * @see #findByXPath(String, QName, Class)
     */
    public Optional<Boolean> findBoolean(final String xPath) {
        return findByXPath(xPath, XPathConstants.BOOLEAN, Boolean.class);
    }

    /**
     * Find a element by using the {@link XPath}.
     *
     * @param xPath      for searching.
     * @param name       of the type.
     * @param returnType the type of the object which will be returned.
     * @return the found value.
     */
    public <O> Optional<O> findByXPath(final String xPath, final QName name, final Class<O> returnType) {
        final Object evaluate;
        try {
            evaluate = XPathFactory.newInstance().newXPath().evaluate(xPath, xmlDoc, name);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            LOG.warn(e);
            return Optional.empty();
        }
        return Optional.ofNullable(returnType.cast(evaluate));
    }

    /**
     * Streams all the elements from the specified xpath. The elements are the indexed path.
     *
     * @param path to the xpath elements.
     * @return the stream.
     */
    public Stream<String> getXPathStream(final String path) {
        final Optional<NodeList> byXPath = findByXPath(path, XPathConstants.NODESET, NodeList.class);
        if (byXPath.isPresent()) {
            return IntStream.rangeClosed(1, byXPath.get().getLength())
                    .parallel()
                    .mapToObj(index -> path + "[" + index + "]");
        }
        return Stream.empty();
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
