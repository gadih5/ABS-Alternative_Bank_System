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
    private int nextPayment;
    private double nextPaymentValue;
    private int numOfDebts;
    private double sumOfDebts;


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
        this.transactions = new ArrayList<Transaction>();
        this.uncompletedTransactions = new ArrayList<Debt>();
        this.finishTimeUnit = 0;
        this.reason = reason;
        this.loanDto = new LoanDto(this);
        this.nextPayment = 0;
        this.nextPaymentValue = 0;
        this.numOfDebts = 0;
        this.sumOfDebts = 0;
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
            //getBorrower().addOutgoingLoan(this);
            isActive = true;
            remainTimeUnit = totalTimeUnit;
            calcNextPayment();
            calcNextPaymentValue();
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
            updateLoanDto();

        } catch (NegativeBalanceException e) {
            throw new NegativeBalanceException("");
        }
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

    public Status getStatus() {
        return status;
    }

    public double getStartLoanAmount() {
        return startLoanAmount;
    }

    public double getLoanSum() {
        return loanSum;
    }

    public int getStartTimeUnit() {
        return startTimeUnit;
    }

    public int getNumOfDebts() {
        calcNumOfDebts();
        return numOfDebts;
    }

    public double getSumOfDebts() {
        return sumOfDebts;
    }

    private void calcNumOfDebts() {
        int numOfDebts = 0;
        double sumOfDebts = 0;
        for(Debt debt:getUncompletedTransactions()){
            numOfDebts++;
            sumOfDebts += debt.getAmount();
        }
        this.numOfDebts = numOfDebts;
        this.sumOfDebts = sumOfDebts;
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

    protected Collection getLoaners() {
        return fractions;
    }

    public int getRemainTimeUnit() {
        return remainTimeUnit;
    }

    protected double getCurrentInterest() {
        return currentInterest;
    }

    public double getNextPaymentValue() {
        calcNextPaymentValue();
        return nextPaymentValue;
    }

    public int getNextPayment() {
        //calcNextPayment();
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

    public ArrayList<Debt> getUncompletedTransactions() {
        return uncompletedTransactions;
    }

    private void payDebts(ArrayList<Debt> debts) throws NegativeBalanceException{
        for(Debt debt:debts) {
            if (debt.getAmount() <= this.getBorrower().getBalance()) {
                LoanTransaction newLoanTransaction = new LoanTransaction(this.borrower, debt.getToCustomer(), debt.getFundPart(), debt.getInterestPart());
                debts.remove(debt);
                transactions.add(newLoanTransaction);
/*                this.uncompletedTransactions = debts;
                this.remainFund -= debt.getFundPart();
                this.remainInterest -= debt.getInterestPart();
                this.currentFund += debt.getFundPart();
                this.currentInterest += debt.getInterestPart();*/
                payDebts(uncompletedTransactions);
                break;
            }
            if (uncompletedTransactions.isEmpty()) {
                setStatus(Status.Active);
                break;
            }
        }
    }

    public void update() throws NegativeBalanceException {
        if(isActive&&remainTimeUnit>=0) {
            remainTimeUnit--;

        }
        if(this.getStatus() != Status.Finished && Bank.getGlobalTimeUnit() != startTimeUnit && isActive() && (remainTimeUnit >= 0 )&& (((Bank.getGlobalTimeUnit() - startTimeUnit)%paymentFrequency==0) || paymentFrequency == 1))
        {
            if (this.status == status.Risk) {
                //Collections.sort(uncompletedTransactions);
                //payDebts(uncompletedTransactions);
            }
            double debtAmount = 0;

            for(Debt debt: uncompletedTransactions){
                debtAmount += debt.getAmount();
            }
            if(debtAmount != getLoanSum() + getLoanSum()*(((double)getInterestPercent()/100.0))){
                for (Fraction fraction : fractions) {
                    //LoanTransaction newLoanTransaction = null;
                    //try {
                        if(fraction.getConvertTime() != Bank.getGlobalTimeUnit()) {
                            PreTransaction preTransaction = null;
                            double fundPart = fraction.getAmount() / (totalTimeUnit / paymentFrequency);
                            double interestPart = fraction.getAmount() * (((double) interestPercent / 100)) / (totalTimeUnit / paymentFrequency);
                            preTransaction = new PreTransaction(fraction.getCustomer(), Bank.getGlobalTimeUnit(), fundPart, interestPart, this);


                            uncompletedTransactions.add(new Debt(fraction.getCustomer(), fraction.getAmount() / (totalTimeUnit / paymentFrequency), fraction.getAmount() * ((double)((interestPercent/100))) / (totalTimeUnit / paymentFrequency)));
                            setStatus(Status.Risk); //TODO make loan in risk status when at least one payment is unpaid

                            //newLoanTransaction = new LoanTransaction(this.borrower, fraction.getCustomer(), fundPart, interestPart);
                            getBorrower().addPreTransaction(preTransaction);
                            fraction.setConvertTime(Bank.getGlobalTimeUnit());
                            calcNextPayment();
                            calcNextPaymentValue();
                        }
                       // fraction.getCustomer().addPreTransaction(preTransaction);
                        //transactions.add(newLoanTransaction);


                    /* catch (NegativeBalanceException e) {

                        if (debtAmount < getRemainFund() + getRemainInterest()) {
                            uncompletedTransactions.add(new Debt(fraction.getCustomer(), fraction.getAmount() / (totalTimeUnit / paymentFrequency), fraction.getAmount() * ((double)((interestPercent/100))) / (totalTimeUnit / paymentFrequency)));
                            setStatus(Status.Risk); //TODO make loan in risk status when at least one payment is unpaid
                        }
                        throw new NegativeBalanceException("Negative Balance: " + this.getBorrowerName() + "'s account not have enough balance pay to " + fraction.getCustomerName() + " for \"" + getLoanName() + "\" loan!");
                    }*/

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

    private void calcNextPaymentValue() {
        //TODO fix this calc..
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
        boolean allPaid = true;
        for(Customer customer: listOfCustomers){
            for(PreTransaction preTransaction: customer.getPreTransactions()){
                if(preTransaction.getLoan() == this) {
                    if (!preTransaction.isPaid()){
                        allPaid = false;

                    }else{
                        setStatus(Status.Active);
                    }
                }
            }
        }

    }

    public void clearAllDebts() {
        uncompletedTransactions.clear();
    }
}
