
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-04 16:06
 */

public class XpathQuery {
    public static void main(String[] args) {

        if(args.length < 2){
            System.out.println("xpath htmlFilePath");
        }
        Long start = System.currentTimeMillis();
        System.out.print(getValueByXpath(args[0], args[1]));
        if(args.length == 3){
            System.out.println(System.currentTimeMillis() - start);
        }
    }


    /**
     * Extract value by xPath from HTML.
     */
    private static String getValueByXpath(String xPath, String html) {

        String value = null;
        try {
            TagNode tagNode = new HtmlCleaner().clean(new File(html));
            Document doc = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
            XPath xpath = XPathFactory.newInstance().newXPath();
            value = (String) xpath.evaluate(xPath, doc, XPathConstants.STRING);
        } catch (Exception e) {
            System.out.println("Extract value error. " + e.getMessage());
            e.printStackTrace();
        }
        return value;
    }
}
