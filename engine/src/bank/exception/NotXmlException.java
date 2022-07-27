package bank.exception;

public class NotXmlException extends Exception {
    public NotXmlException(String message) {
        super(message);
        msg=message;
    }
    String msg;
    @Override
    public String toString() {
        return msg;
    }
}