import app.Parsers.ClientSaxParser;
import app.bean.Client;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {


    public Main() throws ParserConfigurationException, SAXException {
    }

    private static boolean validateXMLByXSD(File xml, File xsd) {
        try {
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(xsd)
                    .newValidator()
                    .validate(new StreamSource(xml));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }


    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        var xsd = new File("").getAbsolutePath() + "\\src\\data\\client.xsd";
        var xml = new File("").getAbsolutePath() + "\\src\\data\\client.xml";

        if (!validateXMLByXSD(new File(xml), new File(xsd))) {
            System.out.println("Invalid format xml");
            System.exit(0);
        }

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        ClientSaxParser saxp = new ClientSaxParser();

        parser.parse(new File(xml), saxp);

        List<Client> clients = saxp.getData();

        for (var v : clients) {
            System.out.println(v);
        }
    }
}