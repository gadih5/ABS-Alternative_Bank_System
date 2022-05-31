package bank;

import bank.exception.NegativeBalanceException;

public class PreTransaction {
    private Loan loan;
    private Customer toCustomer;
    private double fundPart;
    private double interestPart; //positive value
    private boolean paid = false;

    public PreTransaction(Customer toCustomer, double fundPart, double interestPart, Loan loan){
        this.fundPart = fundPart;
        this.interestPart = interestPart;
        this.toCustomer = toCustomer;
        this.loan = loan;
    }

    public Loan getLoan() {
        return loan;
    }

    public Customer getToCustomer() {
        return toCustomer;
    }


    public void makeTransaction(Customer fromCustomer) throws NegativeBalanceException {
        new Transaction(fromCustomer,toCustomer,fundPart+interestPart);
        loan.setRemainFund(-fundPart);
        loan.setRemainInterest(-interestPart);
        loan.setCurrentFund(fundPart);
        loan.setCurrentInterest(interestPart);
        paid = true;
    }
}
