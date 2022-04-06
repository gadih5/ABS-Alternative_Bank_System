package bank;

import bank.exception.NegativeBalanceException;

public class LoanTransaction {
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
        this.transaction = new Transaction(toCustomer, fromCustomer,-(fundPart + interestPart));

        this.fundPart = fundPart;
        this.interestPart = interestPart;
    }


}
