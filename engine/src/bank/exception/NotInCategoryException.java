package bank.exception;

public class NotInCategoryException extends Exception {
    public NotInCategoryException(String message) {

        super(message);
        msg=message;
    }

    String msg;
    @Override
    public String toString() {
        return msg;
    }
    }

