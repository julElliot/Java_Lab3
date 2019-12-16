package app.servlets;

import app.Parsers.ClientDomParser;
import app.Parsers.ClientSaxParser;
import app.Parsers.ClientStAXParser;
import app.XMLVerifier.XMLVerifier;
import app.bean.Client;
import org.xml.sax.SAXException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ListServlet extends HttpServlet {

    private static final int PAGE_SIZE = 2;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {

        var xsdPath =  "";
        var xmlPath =  "";
        var xmlVerifier = new XMLVerifier();

        if (!xmlVerifier.validate(new File(xmlPath), new File(xsdPath))){
            System.out.println("Invalid format xmlPath");
            System.exit(0);
        }

        /************************  SAX parser   *****************/
//        var factory = SAXParserFactory.newInstance();
//        SAXParser parser = null;
//
//        try {
//            parser = factory.newSAXParser();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        }

//        var saxp = new ClientSaxParser();
//
//        try {
//            parser.parse(new File(xmlPath), saxp);
//        } catch (SAXException e) {
//            e.printStackTrace();
//        }
//
//        var clients = saxp.getData();

        /********************************************************/

        /************************  DOM parser   *****************/
//        var dom = new ClientDomParser(xmlPath, xsdPath);
//        List<Client> clients = null;
//        clients = dom.getData();
        /********************************************************/


        /************************  StAX parser   *****************/
        List<Client> clients = null;
        try {
            var StAX = new ClientStAXParser(Files.newInputStream(Paths.get(xmlPath)));
            clients = StAX.getData();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        /********************************************************/


        int index = Integer.parseInt(req.getParameter("pageIndex") == null ? "1" : req.getParameter("pageIndex"));

        if (clients != null){
            req.setAttribute("clients", clients.subList((index - 1) * PAGE_SIZE, Math.min(index * PAGE_SIZE, clients.size())));
            req.setAttribute("pageSize", PAGE_SIZE);
            int pageCount = clients.size() / PAGE_SIZE;
            int mod = (clients.size() % PAGE_SIZE) == 0 ? 0 : 1;
            req.setAttribute("pageCount", pageCount + mod);
            req.setAttribute("pageIndex", index);
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/list.jsp");
        requestDispatcher.forward(req, resp);
    }
}
