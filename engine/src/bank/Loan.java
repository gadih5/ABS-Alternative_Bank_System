package bank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import bank.exception.*;

public class Loan {
    private final double startLoanAmount;
    private final String loanName;
    private Customer borrower;
    private Status status;
    private double loanSum;
    private int startTimeUnit;
    private int totalTimeUnit;
    private int remainTimeUnit;
    private int finishTimeUnit;
    private Reason reason;
    private double interestPercent;
    private int paymentFrequency;
    private Collection<Fraction> fractions;
    private double currentInterest;
    private double remainInterest;
    private double currentFund;
    private double remainFund;
    private boolean isActive;
    private Collection transactions;
    private double amountToComplete;
    private ArrayList<Debt> uncompletedTransactions;
    private LoanDto loanDto;


    public Loan(String loanName, Customer borrower, double loanSum, int totalTimeUnit, String reason, double interestPercent, int paymentFrequency) throws UndefinedReasonException, NegativeLoanSumException, NegativeTotalTimeUnitException, NegativeInterestPercentException, NegativePaymentFrequencyException, OverPaymentFrequencyException, UndividedPaymentFrequencyException {
        this.loanName = loanName;
        this.borrower = borrower;
        setLoanSum(loanSum);
        this.totalTimeUnit = totalTimeUnit;
        this.borrower = borrower;
        setInterestPercent(interestPercent);
        setPaymentFrequency(paymentFrequency);
        this.status = Status.Pending;
        setTotalTimeUnit(totalTimeUnit);
        this.currentInterest =0;
        this.remainInterest = loanSum * interestPercent;
        this.startLoanAmount = loanSum + remainInterest;
        this.currentFund = 0;
        this.remainFund = loanSum;
        this.amountToComplete=loanSum;
        this.isActive = false;
        this.fractions = new ArrayList<Fraction>();
        this.transactions = new ArrayList<Transaction>();
        this.uncompletedTransactions = new ArrayList<Debt>();
        this.finishTimeUnit = 0;
        setReason(reason);
        this.loanDto = new LoanDto(this);
    }

    private void setPaymentFrequency(int paymentFrequency) throws NegativePaymentFrequencyException, OverPaymentFrequencyException,UndividedPaymentFrequencyException {
        if(paymentFrequency <= 0)
            throw new NegativePaymentFrequencyException(this.getLoanName() + " payment frequency cannot be non-positive value!");
        if(paymentFrequency > totalTimeUnit)
            throw new OverPaymentFrequencyException(this.getLoanName() + " payment frequency cannot be greater than total loan time!");
        if(totalTimeUnit%paymentFrequency != 0 && paymentFrequency!=1)
            throw new UndividedPaymentFrequencyException(this.getLoanName() + " payment frequency must divide the total loan time!");
        else
            this.paymentFrequency = paymentFrequency;
    }

    private void setInterestPercent(double interestPercent) throws NegativeInterestPercentException {
        if(interestPercent <= 0)
            throw new NegativeInterestPercentException(this.getLoanName() + " interest percent cannot be non-positive value!");
        else
            this.interestPercent = interestPercent;
    }

    private void setTotalTimeUnit(int totalTimeUnit) throws NegativeTotalTimeUnitException {
        if(loanSum <= 0)
            throw new NegativeTotalTimeUnitException(this.getLoanName() + " total time unit cannot be non-positive value!");
        else
            this.totalTimeUnit = totalTimeUnit;

    }

    private void setLoanSum(double loanSum) throws NegativeLoanSumException {
        if(loanSum <= 0)
            throw new NegativeLoanSumException(this.getLoanName() + " sum cannot be non-positive value!");
        else
            this.loanSum = loanSum;

    }

    public void setStatus(Status status) {
        if(this.status == Status.Pending && status == Status.Active) {
            setStartTimeUnit();
            getBorrower().addOutgoingLoan(this);
            isActive = true;
            remainTimeUnit = totalTimeUnit;
        }
        if(status == Status.Finished){
            setFinishTimeUnit();
        }
        this.status = status;
    }

    public LoanDto getLoanDto() {
        return loanDto;
    }

    private void setFinishTimeUnit() {
        this.finishTimeUnit = Bank.getGlobalTimeUnit();
    }

    public void setStartTimeUnit() {
        this.startTimeUnit = Bank.getGlobalTimeUnit();
    }

    private void checkStatus(){
        if(currentFund == loanSum){
            setStatus(Status.Active);
        }
    }

    public void addLoaner(Customer customer, double amount) throws NegativeBalanceException {
        Fraction newFraction = new Fraction(customer,amount);
        fractions.add(newFraction);
        customer.addIngoingLoan(this,amount);
        currentFund += newFraction.getAmount();
        amountToComplete-= newFraction.getAmount();

        checkStatus();
        updateLoanDto();
    }

    protected Collection<Fraction> getFractions() {
        return fractions;
    }

    protected Collection getTransactions() {
        return transactions;
    }

    protected String getBorrowerName() {
        return borrower.getName();
    }

    public String getLoanName() {
        return loanName;
    }

    protected Status getStatus() {
        return status;
    }

    public double getStartLoanAmount() {
        return startLoanAmount;
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

    public void setReason(String reason) throws UndefinedReasonException {

        switch (reason){
            case "Setup a business":
                this.reason = Reason.Setup_a_business;
                break;
            case "Overdraft cover":
                this.reason = Reason.Overdraft_cover;
                break;
            case "Investment":
                this.reason = Reason.Investment;
                break;
            case "Happy Event":
                this.reason = Reason.Happy_Event;
                break;
            case "Renovate":
                this.reason = Reason.Renovate;
                break;
            default: throw new UndefinedReasonException(reason + " Undefined");
        }
    }

    protected String getReason() {
        String res = "";
        switch (reason){
            case Setup_a_business:
                res = "Setup a business";
                break;
            case Overdraft_cover:
                res = "Overdraft cover";
                break;
            case Investment:
                res = "Investment";
                break;
            case Happy_Event:
                res = "Happy Event";
                break;
            case Renovate:
                res = "Renovate";
                break;
        }
        return res;
    }

    protected double getInterestPercent() {
        return interestPercent;
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

    public int getFinishTimeUnit() {
        return finishTimeUnit;
    }

    protected boolean isActive() {
        return isActive;
    }

    public ArrayList<Debt> getUncompletedTransactions() {
        return uncompletedTransactions;
    }

    public void update() throws NegativeBalanceException {
        if(this.isActive)
            this.remainTimeUnit--;
        if(this.status==status.Risk){
            Collections.sort(uncompletedTransactions);
            for(Debt debt:uncompletedTransactions){
                if(debt.getAmount() <= this.getBorrower().getBalance()){
                    LoanTransaction newLoanTransaction = new LoanTransaction(this.borrower, debt.getToCustomer(), debt.getFundPart(), debt.getInterestPart());
                    uncompletedTransactions.remove(debt);
                    transactions.add(newLoanTransaction);
                }
            }
            if(this.uncompletedTransactions.isEmpty()){
                setStatus(Status.Active);
            }
        }

        if(this.isActive && (remainTimeUnit >= 0 && ((Bank.getGlobalTimeUnit() - startTimeUnit)%paymentFrequency==0 || paymentFrequency == 1)))
        {
            for(Fraction fraction: fractions)
            {
                LoanTransaction newLoanTransaction = null;
                try {
                    double fundPart = fraction.getAmount()/(totalTimeUnit/paymentFrequency);
                    double interestPart = fraction.getAmount() * interestPercent /(totalTimeUnit/paymentFrequency);
                    newLoanTransaction = new LoanTransaction(this.borrower, fraction.getCustomer(), fundPart,interestPart );
                    transactions.add(newLoanTransaction);
                    this.remainFund -= fundPart;
                    this.remainInterest -= interestPart;

                } catch (NegativeBalanceException e) {
                   uncompletedTransactions.add(new Debt(fraction.getCustomer(),fraction.getAmount()/(totalTimeUnit/paymentFrequency),fraction.getAmount() * interestPercent /(totalTimeUnit/paymentFrequency)));
                   throw new NegativeBalanceException("Negative Balance " + this.getBorrowerName() +" to "+fraction.getCustomerName());
                }

            }
        }
        if(this.remainTimeUnit == 0 && this.status != Status.Risk && this.remainInterest == 0 && this.remainFund == 0){
            setStatus(Status.Finished);
        }
        this.updateLoanDto();
    }

    void updateLoanDto(){
        this.loanDto = new LoanDto(this);
    }
}
