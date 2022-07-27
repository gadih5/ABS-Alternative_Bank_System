package bank.exception;

public class UndefinedReasonException extends Exception {
    public UndefinedReasonException(String message) {
        super(message);
        msg=message;
    }
    String msg;
    @Override
    public String toString() {
        return msg;
    }
}