package bank.exception;

public class UndividedPaymentFrequencyException extends Exception {
    public UndividedPaymentFrequencyException(String message) {

        super(message);
        msg=message;
    }

    String msg;
    @Override
    public String toString() {
        return msg;
    }
}