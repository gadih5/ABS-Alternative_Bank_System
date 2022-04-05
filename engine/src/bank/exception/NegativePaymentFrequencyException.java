package bank.exception;

public class NegativePaymentFrequencyException extends Exception {
    public NegativePaymentFrequencyException(String message) {

        super(message);
        msg=message;
    }

    String msg;
    @Override
    public String toString() {
        return msg;
    }
}