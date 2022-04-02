package bank.exception;

public class NegativePaymentFrequencyException extends Exception {
    public NegativePaymentFrequencyException(String message) {
        super(message);
    }
}
