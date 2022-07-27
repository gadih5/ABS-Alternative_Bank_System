package bank.exception;

public class UndefinedCustomerException extends Exception{
    public UndefinedCustomerException(String message) {
        super(message);
        msg=message;
    }
    String msg;
    @Override
    public String toString() {
        return msg;
    }
}