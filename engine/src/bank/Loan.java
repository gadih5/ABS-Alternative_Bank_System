package bank;

import java.util.ArrayList;
import java.util.Collection;
import bank.Bank;
import bank.Fraction;
import bank.exception.NegativeBalanceException;

public class Loan {
    private String loanName;
    private String borrowerName;
    private Customer borrower;
    private Status status;
    private double loanSum;
    private int startTimeUnit;
    private int totalTimeUnit;
    private int remainTimeUnit;
    private Type reason;
    private double interestPrecent;
    private int paymentFrequency;
    private Collection<Fraction> fractions;
    private double currentInterest;
    private double remainInterest;
    private double currentFund;
    private double remainFund;
    private boolean isActive;
    private Collection transactions;


    public Loan(String loanName, Customer borrower, double loanSum, int totalTimeUnit, Type reason, double interestPrecent, int paymentFrequency) {
        this.loanName = loanName;
        this.borrower = borrower;
        this.borrowerName = borrower.getName();
        this.loanSum = loanSum;
        this.totalTimeUnit = totalTimeUnit;
        this.reason = reason;
        this.interestPrecent = interestPrecent;
        this.paymentFrequency = paymentFrequency;
        this.status = Status.Pending;
        this.remainTimeUnit = totalTimeUnit;
        this.currentInterest =0;
        this.remainInterest = loanSum * interestPrecent;
        this.currentFund = 0;
        this.remainFund = loanSum;
        this.isActive = false;
        this.fractions = new ArrayList<Fraction>();
        this.transactions = new ArrayList<Transaction>();
    }

    public void setStatus(int globalTimeUnit,Status status) {
        if(this.status == Status.Pending && status == Status.Active) {
            setStartTimeUnit(globalTimeUnit);
        }
        this.status = status;

    }

    public void setStartTimeUnit(int globalTimeUnit) {
        this.startTimeUnit = globalTimeUnit;
    }

    private void checkStatus(int globalTimeUnit){
        if(currentInterest == loanSum){
            setStatus(globalTimeUnit,Status.Active);
        }
    }
    private void addLoaner(int globalTimeUnit,Customer customer, double amount){
        Fraction newFraction = new Fraction(customer,amount);
        fractions.add(newFraction);
        currentInterest += newFraction.getAmount();
        checkStatus(globalTimeUnit);

    }


    private String getSerialNumber() {
        return loanName;
    }

    private String getBorrowerName() {
        return borrowerName;
    }

    private Status getStatus() {
        return status;
    }

    private double getLoanSum() {
        return loanSum;
    }

    private int getStartTimeUnit() {
        return startTimeUnit;
    }

    private int getTotalTimeUnit() {
        return totalTimeUnit;
    }

    private Type getReason() {
        return reason;
    }

    private double getInterestPrecent() {
        return interestPrecent;
    }

    private int getPaymentFrequency() {
        return paymentFrequency;
    }

    public Customer getBorrower() {
        return borrower;
    }

    private Collection getLoaners() {
        return fractions;
    }

    private int getRemainTimeUnit() {
        return remainTimeUnit;
    }

    private double getCurrentInterest() {
        return currentInterest;
    }

    private double getRemainInterest() {
        return remainInterest;
    }

    private double getCurrentFund() {
        return currentFund;
    }

    private double getRemainFund() {
        return remainFund;
    }

    private boolean isActive() {
        return isActive;
    }

    public void update(int globalTimeUnit) throws NegativeBalanceException {
        this.remainTimeUnit--;
        if(this.isActive && remainTimeUnit >= 0 && ((globalTimeUnit - startTimeUnit)%paymentFrequency==0 || paymentFrequency == 1))
        {
            for(Fraction fraction: fractions)
            {
                Transaction newTransaction = new Transaction(globalTimeUnit,this.borrower, fraction.getCustomer(),
                                                        fraction.getAmount()/(totalTimeUnit/paymentFrequency)
                                             + fraction.getAmount() * interestPrecent/(totalTimeUnit/paymentFrequency) );
            }
        }
    }
}
