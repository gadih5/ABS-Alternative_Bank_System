package bank;

import _json.LoanTransaction_json;
import bank.exception.NegativeBalanceException;

import java.io.Serializable;

public class LoanTransaction implements Serializable {
    private Transaction transaction;
    private double fundPart;
    private double interestPart;


    public int getTimeUnit(){
        return transaction.getTimeUnit();
    }
    public double getFundPart() {
        return fundPart;
    }

    public double getInterestPart() {
        return interestPart;
    }


    public LoanTransaction(Customer fromCustomer, Customer toCustomer, double fundPart, double interestPart) throws NegativeBalanceException {
        this.transaction = new Transaction(fromCustomer, toCustomer,fundPart + interestPart);
        fromCustomer.addTransaction(this.transaction);

        this.fundPart = fundPart;
        this.interestPart = interestPart;
    }

 /*   public LoanTransaction(LoanTransaction_json loanTransaction_json) {
        this.transaction = new Transaction(loanTransaction_json.transaction);
        this.fundPart = loanTransaction_json.fundPart;
        this.interestPart = loanTransaction_json.interestPart;
    }*/
}
