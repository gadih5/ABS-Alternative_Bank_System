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
    private double amountToComplete;
    private Collection uncompletedTransactions;


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
        this.amountToComplete=loanSum;
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
        amountToComplete-= newFraction.getAmount();
        checkStatus(globalTimeUnit);

    }

    protected Collection<Fraction> getFractions() {
        return fractions;
    }

    protected Collection getTransactions() {
        return transactions;
    }

    protected String getSerialNumber() {
        return loanName;
    }

    protected String getBorrowerName() {
        return borrowerName;
    }

    protected Status getStatus() {
        return status;
    }

    protected double getLoanSum() {
        return loanSum;
    }

    protected int getStartTimeUnit() {
        return startTimeUnit;
    }

    protected int getTotalTimeUnit() {
        return totalTimeUnit;
    }

    protected Type getReason() {
        return reason;
    }

    protected double getInterestPrecent() {
        return interestPrecent;
    }

    protected int getPaymentFrequency() {
        return paymentFrequency;
    }

    public Customer getBorrower() {
        return borrower;
    }

    protected Collection getLoaners() {
        return fractions;
    }

    protected int getRemainTimeUnit() {
        return remainTimeUnit;
    }

    protected double getCurrentInterest() {
        return currentInterest;
    }

    protected double getRemainInterest() {
        return remainInterest;
    }

    protected double getCurrentFund() {
        return currentFund;
    }

    protected double getRemainFund() {
        return remainFund;
    }

    public double getAmountToComplete() {
        return amountToComplete;
    }

    protected boolean isActive() {
        return isActive;
    }

    public void update(int globalTimeUnit) throws NegativeBalanceException {

        //add debts payments first.+if debts is 0 and loan in risk change to Active
        //Cheack Status Update Statue
        this.remainTimeUnit--;
        if(this.isActive && remainTimeUnit >= 0 && ((globalTimeUnit - startTimeUnit)%paymentFrequency==0 || paymentFrequency == 1))
        {
            for(Fraction fraction: fractions)
            {
                LoanTransaction newLoanTransaction = null;
                try {
                    newLoanTransaction = new LoanTransaction(globalTimeUnit,this.borrower, fraction.getCustomer(),
                                                            fraction.getAmount()/(totalTimeUnit/paymentFrequency)
                                                 ,fraction.getAmount() * interestPrecent/(totalTimeUnit/paymentFrequency) );
                    transactions.add(newLoanTransaction);
                } catch (NegativeBalanceException e) {
                   uncompletedTransactions.add(new Debt(fraction.getCustomer(),fraction.getAmount()/(totalTimeUnit/paymentFrequency)+fraction.getAmount() * interestPrecent/(totalTimeUnit/paymentFrequency)));
                   throw new NegativeBalanceException("Negative Balance " + this.borrowerName+" to "+fraction.getCustomerName());
                }

            }
        }
    }
}
