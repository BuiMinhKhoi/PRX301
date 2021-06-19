package khoibm.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.helpers.DefaultHandler;

public class XMLUtils {

    //DOM
    public static Document parseFileToDOM(String filePath) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        File f = new File(filePath);
        Document doc = db.parse(f);

        return doc;
    }

    public static XPath createXPath() {
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xp = xpf.newXPath();

        return xp;
    }

    public static boolean transformDomToStreamResult(Node node, String filePath)
            throws Exception {
        Source source = new DOMSource(node);
        File file = new File(filePath);

        Result result = new StreamResult(file);
        TransformerFactory tf = TransformerFactory.newInstance();

        Transformer trans = tf.newTransformer();
        trans.transform(source, result);

        return true;

    }

    //SAX
    public static void parseFileWithSAX(String filePath, DefaultHandler defaultHandler)
            throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser parser = spf.newSAXParser();

        File file = new File(filePath);
        parser.parse(file, defaultHandler);

    }

    //StAX
    public static XMLStreamReader createStAXCursorReadFromFile(String filePath)
            throws Exception {
        XMLInputFactory xif = XMLInputFactory.newFactory();
        File file = new File(filePath);

        InputStream is = new FileInputStream(file);
        XMLStreamReader reader = xif.createXMLStreamReader(is);
        return reader;

    }

    public static String getTextContext(XMLStreamReader reader, String tagName)
            throws Exception {

        if (reader != null) {
            while (reader.hasNext()) {
                int cursor = reader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    String name = reader.getLocalName();

                    if (name.equals(tagName)) {
                        reader.next();
                        String result = reader.getText();

                        return result;
                    }
                }

            }
        }
        return null;
    }
}
