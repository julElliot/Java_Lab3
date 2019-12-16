package app.Parsers;

import app.bean.Client;
import app.bean.Table;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

import java.util.*;

public class ClientSaxParser extends DefaultHandler implements XmlParser<Client> {

    private Client client;
    private String thisElement = "";
    private ArrayList<Client> clients = new ArrayList<>();

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start parse XML...");
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        thisElement = qName;

        if (qName.equals("client")) {
            client = new Client();
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        thisElement = "";
        if (qName.equals("client")) {
            clients.add(client);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (thisElement.equals("id")) {
            client.setId(Integer.parseInt(new String(ch, start, length)));
        } else if (thisElement.equals("first-name")) {
            client.setFirstName(new String(ch, start, length));
        } else if (thisElement.equals("last-name")) {
            client.setLastName(new String(ch, start, length));
        } else if (thisElement.equals("money")) {
            client.setMoney(Double.parseDouble(new String(ch, start, length)));
        } else if (thisElement.equals("table")) {
            client.setTable(new Table());
        } else if (thisElement.equals("table-id")) {
            client.getTable().setId(Integer.parseInt(new String(ch, start, length)));
        } else if (thisElement.equals("number")) {
            client.getTable().setNumber(Integer.parseInt(new String(ch, start, length)));
        } else if (thisElement.equals("is-free")) {
            client.getTable().setFree(Boolean.parseBoolean(new String(ch, start, length)));
        }
    }

    @Override
    public void endDocument() {
        System.out.println("Stop parse XML...");
    }

    @Override
    public List<Client> getData() throws XmlParserException {
        return clients;
    }
}