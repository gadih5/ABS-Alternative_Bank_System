package bank.exception;

public class NegativeTotalTimeUnitException extends Exception {
    public NegativeTotalTimeUnitException(String message) {
        super(message);
        msg=message;
    }
    String msg;
    @Override
    public String toString() {
        return msg;
    }
}