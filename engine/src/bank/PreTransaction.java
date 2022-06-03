package bank;

import bank.exception.NegativeBalanceException;

public class PreTransaction {
    private Loan loan;
    private String loanName;
    private Customer toCustomer;
    private int payTime;
    private double fundPart;
    private double interestPart; //positive value
    private double sum;
    private boolean paid = false;

    public PreTransaction(Customer toCustomer,int payTime, double fundPart, double interestPart, Loan loan){
        this.fundPart = fundPart;
        this.payTime = payTime;
        this.interestPart = interestPart;
        this.toCustomer = toCustomer;
        this.loan = loan;
        this.loanName = loan.getLoanName();
        this.sum = fundPart + interestPart;
    }

    public Loan getLoan() {
        return loan;
    }

    public String getToCustomer() {
        return toCustomer.getName();
    }

    public String getLoanName() {
        return loanName;
    }

    public int getPayTime() {
        return payTime;
    }

    public double getFundPart() {
        return fundPart;
    }

    public double getSum() {
        return sum;
    }

    public double getInterestPart() {
        return interestPart;
    }

    public void makeTransaction(Customer fromCustomer) throws NegativeBalanceException {
        //new Transaction(fromCustomer,toCustomer,fundPart+interestPart);
        fromCustomer.addTransaction(new Transaction(fromCustomer,toCustomer,fundPart+interestPart));
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

    public boolean isPaid() {
        return paid;
    }

}
