package app.Parsers;

public class XmlParserException extends RuntimeException {
    public XmlParserException() {
    }

    XmlParserException(String message) {
        super(message);
    }

    public XmlParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlParserException(Throwable cause) {
        super(cause);
    }
}
