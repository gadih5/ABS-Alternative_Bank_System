package bank.exception;

public class AlreadyExistCustomerException extends Exception {
    public AlreadyExistCustomerException(String message) {
        super(message);
        msg=message;
    }

    String msg;
    @Override
    public String toString() {
        return msg;
    }
}
