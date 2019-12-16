package app.Parsers;

import app.bean.Client;
import app.bean.Table;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;

public class ClientStAXParser implements AutoCloseable, XmlParser<Client> {
    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    private final XMLStreamReader reader;

    private ArrayList<Client> clients = new ArrayList<>();

    @Override
    public ArrayList<Client> getData() throws XmlParserException {

        try {
            while (reader.hasNext()) {       // while not end of XML
                int event = reader.next();   // read next event
                if (event == XMLEvent.START_ELEMENT &&
                        "client".equals(reader.getLocalName())) {
                    var client = new Client();

                    doUntil(XMLEvent.START_ELEMENT, "id");
                    client.setId(Integer.parseInt(reader.getElementText()));

                    doUntil(XMLEvent.START_ELEMENT, "first-name");
                    client.setFirstName(reader.getElementText());

                    doUntil(XMLEvent.START_ELEMENT, "last-name");
                    client.setLastName(reader.getElementText());

                    doUntil(XMLEvent.START_ELEMENT, "money");
                    client.setMoney(Double.parseDouble(reader.getElementText()));

                    doUntil(XMLEvent.START_ELEMENT, "table");
                    client.setTable(new Table());

                    doUntil(XMLEvent.START_ELEMENT, "table-id");
                    client.getTable().setId(Integer.parseInt(reader.getElementText()));

                    doUntil(XMLEvent.START_ELEMENT, "number");
                    client.getTable().setNumber(Integer.parseInt(reader.getElementText()));

                    doUntil(XMLEvent.START_ELEMENT, "is-free");
                    client.getTable().setFree(Boolean.parseBoolean(reader.getElementText()));

                    clients.add(client);
                }
            }
        } catch (XMLStreamException e) {
            throw new XmlParserException(e.getMessage());
        }

        return clients;
    }

    public ClientStAXParser(InputStream is) throws XMLStreamException {
        reader = FACTORY.createXMLStreamReader(is);
    }


    public XMLStreamReader getReader() {
        return reader;
    }

    private boolean doUntil(int stopEvent, String value) throws XMLStreamException {
        while (reader.hasNext()) {
            int event = reader.next();
            if (event == stopEvent && value.equals(reader.getLocalName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (XMLStreamException e) { // empty
            }
        }
    }
}