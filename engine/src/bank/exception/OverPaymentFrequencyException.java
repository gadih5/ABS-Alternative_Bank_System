package bank.exception;

public class OverPaymentFrequencyException extends Exception {
    public OverPaymentFrequencyException(String message) {
        super(message);
        msg=message;
    }

    String msg;
    @Override
    public String toString() {
        return msg;
    }
}