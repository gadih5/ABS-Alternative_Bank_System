package bank.exception;

public class NegativeInterestPercentException extends Exception {
    public NegativeInterestPercentException(String message) {
        super(message);
        msg=message;
    }

    String msg;
    @Override
    public String toString() {
        return msg;
    }
}

