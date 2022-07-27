package bank.exception;

public class NegativeLoanSumException extends Exception{
    public NegativeLoanSumException(String message) {
        super(message);
        msg=message;
    }
    String msg;
    @Override
    public String toString() {
        return msg;
    }
}