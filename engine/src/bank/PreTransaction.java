package bank;

import bank.exception.NegativeBalanceException;

public class PreTransaction {
    private Loan loan;
    private Customer toCustomer;
    private int payTime;
    private double fundPart;
    private double interestPart; //positive value
    private boolean paid = false;

    public PreTransaction(Customer toCustomer,int payTime, double fundPart, double interestPart, Loan loan){
        this.fundPart = fundPart;
        this.payTime = payTime;
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

    @Override
    public String toString() {
        String res = "Loan: " + loan.getLoanName() +
                ", Pay time: " + payTime +
                ", Sum to pay: " + (fundPart+interestPart);
                if(paid)
                    res += " Paid";
                else
                    res += " Unpaid";
        return res;
    }
}
