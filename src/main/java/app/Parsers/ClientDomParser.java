package app.Parsers;

import app.bean.Client;
import app.bean.Table;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientDomParser implements XmlParser<Client> {
    private static final String CLIENT = "client";
    private static final String CLIENTS = "clients";
    private static final String ID = "id";
    private static final String TABLE_ID = "table-id";
    private static final String FIRST_NAME = "first-name";
    private static final String LAST_NAME = "last-name";
    private static final String MONEY = "money";
    private static final String TABLE = "table";
    private static final String TABLE_NUMBER = "number";
    private static final String TABLE_IS_FREE = "is-free";

    private DocumentBuilder documentBuilder;
    private String sourceFilePath;
    private String xsdFilePath;

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public String getXsdFilePath() {
        return xsdFilePath;
    }

    public ClientDomParser() {
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        }
    }

    public ClientDomParser(String sourceFilePath, String xsdFilePath) {
        this();
        this.sourceFilePath = sourceFilePath;
        this.xsdFilePath = xsdFilePath;
    }

    @Override
    public List<Client> getData() throws XmlParserException {
        var sourceFile = new File(sourceFilePath);
        var xsdFile = new File(xsdFilePath);

        if (!sourceFile.exists()) {
            throw new XmlParserException(sourceFilePath + ": file not exists.");
        }

        if (!xsdFile.exists()) {
            throw new XmlParserException(sourceFilePath + ": file not exists.");
        }

        validateXMLByXSD(sourceFile, xsdFile);

        List<Client> clients = new ArrayList<>();
        Document document;

        try {
            document = documentBuilder.parse(sourceFile);
        } catch (SAXException | IOException e) {
            throw new XmlParserException(e.getMessage());
        }

        var element = document.getDocumentElement();
        var nodeClients = element.getElementsByTagName(CLIENT);

        for (var i = 0; i < nodeClients.getLength(); i++) {
            if (nodeClients.item(i).getNodeType() == Node.ELEMENT_NODE) {
                clients.add(getClientElement((Element) nodeClients.item(i)));
            }
        }

        return clients;
    }

    private Client getClientElement(Element element) {
        Client client;

        try {
            client = new Client(Integer.parseInt(getElementTextContent(element, ID)));
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("'" + ID + "'" + "incorrect");
        }

        try {
            client.setMoney(Integer.parseInt(getElementTextContent(element, MONEY)));
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("'" + MONEY + "'" + "incorrect");
        }

        client.setFirstName(getElementTextContent(element, FIRST_NAME));
        client.setLastName(getElementTextContent(element, LAST_NAME));

        var tableElement = (Element) element.getElementsByTagName(TABLE).item(0);
        if (tableElement != null) {
            client.setTable(getTableElement(tableElement));
        }

        return client;
    }

    private static String getElementTextContent(Element element, String elementName) {
        var nList = element.getElementsByTagName(elementName);
        var node = nList.item(0);
        return node.getTextContent();
    }

    private Table getTableElement(Element element) {
        var table = new Table(Integer.parseInt(getElementTextContent(element, TABLE_ID)));

        try {
            table.setFree(Boolean.parseBoolean(getElementTextContent(element, TABLE_IS_FREE)));
        } catch (Exception ex) {
            throw new IllegalArgumentException("'" + TABLE_IS_FREE + "'" + "item incorrect.");
        }

        try {
            table.setNumber(Integer.parseInt(getElementTextContent(element, TABLE_NUMBER)));
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("'" + TABLE_NUMBER + "'" + "incorrect");
        }

        return table;
    }

    private void validateXMLByXSD(File xml, File xsd) throws XmlParserException {
        try {
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(xsd)
                    .newValidator()
                    .validate(new StreamSource(xml));
        } catch (Exception e) {
            throw new XmlParserException("Invalid xml format");
        }
    }
}
