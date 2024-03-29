package bank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import bank.exception.*;

public class Loan implements Serializable {
    private final double startLoanAmount;
    private final String loanName;
    private Customer borrower;
    private Status status;
    private double loanSum;
    private int startTimeUnit;
    private int totalTimeUnit;
    private int remainTimeUnit;
    private int finishTimeUnit;
    private String reason;
    private int interestPercent;
    private int paymentFrequency;
    private ArrayList<Fraction> fractions;
    private ArrayList<PreTransaction> preTransactions;
    private double currentInterest;
    private double remainInterest;
    private double currentFund;
    private double remainFund;
    private boolean isActive;
    private ArrayList<LoanTransaction> transactions;
    private double amountToComplete;
    private ArrayList<Debt> uncompletedTransactions;
    private LoanDto loanDto;
    private int nextPayment;
    private double nextPaymentValue;
    private int numOfDebts;
    private double sumOfDebts;
    private ArrayList<String> loanersNames;

    public Loan(String loanName, Customer borrower, double loanSum, int totalTimeUnit, String reason, int interestPercent, int paymentFrequency) throws UndefinedReasonException, NegativeLoanSumException, NegativeTotalTimeUnitException, NegativeInterestPercentException, NegativePaymentFrequencyException, OverPaymentFrequencyException, UndividedPaymentFrequencyException {
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
        this.remainInterest = loanSum * ((double)((interestPercent/100)));
        this.startLoanAmount = loanSum + remainInterest;
        this.currentFund = 0;
        this.remainFund = loanSum;
        this.amountToComplete=loanSum;
        this.isActive = false;
        this.fractions = new ArrayList<Fraction>();
        this.transactions = new ArrayList<LoanTransaction>();
        this.uncompletedTransactions = new ArrayList<Debt>();
        this.finishTimeUnit = 0;
        this.reason = reason;
        this.loanDto = new LoanDto(this);
        this.nextPayment = 0;
        this.nextPaymentValue = 0;
        this.numOfDebts = 0;
        this.sumOfDebts = 0;
        this.loanersNames = new ArrayList<String>();
        this.preTransactions=new ArrayList<PreTransaction>();
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

    private void setInterestPercent(int interestPercent) throws NegativeInterestPercentException {
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

    public String getReason() {
        return reason;
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
            isActive = true;
            remainTimeUnit = totalTimeUnit;
            calcNextPayment();
            calcNextPaymentValue();
        }
        if(status == Status.Finished){
            setFinishTimeUnit();
        }
        this.status = status;
        this.updateLoanDto();
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

    public void checkStatus(){
        if(amountToComplete == 0){
            setStatus(Status.Active);
        }
    }

    public void addLoaner(Customer customer, double amount) throws NegativeBalanceException {
        try {
            customer.addIngoingLoan(this,amount);
            Fraction newFraction = new Fraction(customer,amount);
            fractions.add(newFraction);
            amountToComplete -= newFraction.getAmount();
            checkStatus();
            this.addLoanerName(newFraction.getCustomerName());
            updateLoanDto();
            customer.updateCustomerDto();
        } catch (NegativeBalanceException e) {
            throw new NegativeBalanceException("");
        }
    }

    public Collection<Fraction> getFractions() {
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

    public Status getStatus() {
        return status;
    }

    public double getLoanSum() {
        return loanSum;
    }

    public int getStartTimeUnit() {
        return startTimeUnit;
    }

    public int getTotalTimeUnit() {
        return totalTimeUnit;
    }

    public int getInterestPercent() {
        return interestPercent;
    }

    public int getPaymentFrequency() {
        return paymentFrequency;
    }

    public Customer getBorrower() {
        return borrower;
    }

    protected double getCurrentInterest() {
        return currentInterest;
    }

    public double getNextPaymentValue() {
        calcNextPaymentValue();
        return nextPaymentValue;
    }

    public int getNextPayment() {
        return nextPayment;
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

    public boolean isActive() {
        return isActive;
    }

    public void update() {
        if(isActive&&remainTimeUnit>=0) {
            remainTimeUnit--;
        }
        if(this.getStatus() != Status.Finished && Bank.getGlobalTimeUnit() != startTimeUnit && isActive() && (remainTimeUnit >= 0 )&& (((Bank.getGlobalTimeUnit() - startTimeUnit)%paymentFrequency==0) || paymentFrequency == 1))
        {
            double debtAmount = 0;
            for(Debt debt: uncompletedTransactions){
                debtAmount += debt.getAmount();
            }
            if(debtAmount != getLoanSum() + getLoanSum()*(((double)getInterestPercent()/100.0))){
                for (Fraction fraction : fractions) {
                        if(fraction.getConvertTime() != Bank.getGlobalTimeUnit()) {
                            PreTransaction preTransaction = null;
                            double fundPart = fraction.getAmount() / (totalTimeUnit / paymentFrequency);
                            double interestPart = fraction.getAmount() * (((double) interestPercent / 100)) / (totalTimeUnit / paymentFrequency);
                            preTransaction = new PreTransaction(fraction.getCustomer().getCustomerDto(), Bank.getGlobalTimeUnit(), fundPart, interestPart, this.getLoanDto());
                            this.preTransactions.add(preTransaction);

                            uncompletedTransactions.add(new Debt(fraction.getCustomer(), fraction.getAmount() / (totalTimeUnit / paymentFrequency), fraction.getAmount() * ((double)((interestPercent/100))) / (totalTimeUnit / paymentFrequency)));
                            setStatus(Status.Risk);

                            getBorrower().addPreTransaction(preTransaction);
                            fraction.setConvertTime(Bank.getGlobalTimeUnit());
                            calcNextPayment();
                            calcNextPaymentValue();
                        }
                }
            }
        }
        if(this.remainTimeUnit == 0 && this.status != Status.Risk && this.remainInterest == 0 && this.remainFund == 0){
            setStatus(Status.Finished);
        }
        this.updateLoanDto();
    }

    public void updateLoanDto(){
        this.loanDto = new LoanDto(this);
    }

    public void calcNextPayment() {
        if((paymentFrequency == 1) || (((Bank.getGlobalTimeUnit() - startTimeUnit)%paymentFrequency)==0)){
            nextPayment = Bank.getGlobalTimeUnit()+ paymentFrequency;
        }
        else if(Bank.getGlobalTimeUnit() == startTimeUnit){
            nextPayment = Bank.getGlobalTimeUnit() + paymentFrequency;
        }
        else{
            nextPayment = (Bank.getGlobalTimeUnit() - startTimeUnit)%paymentFrequency + Bank.getGlobalTimeUnit();
        }
    }

    public ArrayList<String> getLoanersNames() {
        return loanersNames;
    }

    public void addLoanerName(String name){
        this.loanersNames.add(name);
    }

    private void calcNextPaymentValue() {
        double nextPayment = 0;
        for (Fraction fraction : fractions) {
            double fundPart = fraction.getAmount() / (getTotalTimeUnit() / getPaymentFrequency());
            double interestPart = fraction.getAmount() * (getInterestPercent()/100.0) / (double)(getTotalTimeUnit() / getPaymentFrequency());
            nextPayment += fundPart + interestPart;
        }
        nextPaymentValue = nextPayment;
    }

    public void setCurrentInterest(double currentInterest) {
        this.currentInterest = currentInterest;
    }

    public void setRemainInterest(double remainInterest) {
        this.remainInterest = remainInterest;
    }

    public void setCurrentFund(double currentFund) {
        this.currentFund = currentFund;
    }

    public void setRemainFund(double remainFund) {
        this.remainFund = remainFund;
    }

    public void checkRiskStatus(Collection<Customer> listOfCustomers) {
        for(PreTransaction preTransaction: this.preTransactions){
            if (!preTransaction.isPaid()){
                setStatus(Status.Risk);
                break;
            }else if(this.remainTimeUnit == 0 && this.status != Status.Risk && this.remainInterest == 0 && this.remainFund == 0){
                setStatus(Status.Finished);
            }else{

                setStatus(Status.Active);
            }
        }
        updateLoanDto();
    }

    public void clearAllDebts() {
        uncompletedTransactions.clear();
    }
}