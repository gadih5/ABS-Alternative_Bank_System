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


    public LoanTransaction(int globalTimeUnit, Customer fromCustomer, Customer toCustomer, double fundPart, double interestPart) throws NegativeBalanceException {
        this.transaction = new Transaction(globalTimeUnit, fromCustomer, toCustomer,fundPart + interestPart);
        this.fundPart = fundPart;
        this.interestPart = interestPart;
    }


}
