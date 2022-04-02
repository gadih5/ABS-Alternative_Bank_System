package bank.exception;

public class NegativeInterestPercentException extends Exception {
    public NegativeInterestPercentException(String message) {
        super(message);
    }
}

