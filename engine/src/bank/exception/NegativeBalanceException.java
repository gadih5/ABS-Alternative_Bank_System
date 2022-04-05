package bank.exception;

public class NegativeBalanceException extends Exception {

    public NegativeBalanceException(String message) {
        super(message);
        msg=message;
    }

    String msg;
    @Override
    public String toString() {
        return msg;
    }
}
