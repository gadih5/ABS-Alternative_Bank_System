package bank;

import bank.exception.NegativeBalanceException;
import java.io.Serializable;

public class LoanTransaction implements Serializable {
    private Transaction transaction;
    private double fundPart;
    private double interestPart;

    public LoanTransaction(Customer fromCustomer, Customer toCustomer, double fundPart, double interestPart) throws NegativeBalanceException {
        this.transaction = new Transaction(fromCustomer, toCustomer,fundPart + interestPart);
        fromCustomer.addTransaction(this.transaction);
        this.fundPart = fundPart;
        this.interestPart = interestPart;
    }
}