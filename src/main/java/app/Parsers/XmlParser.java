package app.Parsers;

import java.util.List;

public interface XmlParser<T> {

    List<T> getData() throws XmlParserException;
}
