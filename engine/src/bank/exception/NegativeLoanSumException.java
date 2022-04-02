package bank.exception;

public class NegativeLoanSumException extends Exception{
    public NegativeLoanSumException(String message) {
        super(message);
    }
}
