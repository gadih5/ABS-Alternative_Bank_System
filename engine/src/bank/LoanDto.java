package bank;

import _json.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class LoanDto implements Serializable {
    private String loanName;
    private String borrowerName;
    private Status status;
    private double loanSum;
    private int startTimeUnit;
    private int totalTimeUnit;
    private int remainTimeUnit;
    private int finishTimeUnit;
    private String reason;
    private int interestPrecent;
    private int paymentFrequency;
    private Collection<Fraction> fractions;
    private double currentInterest;
    private double remainInterest;
    private double currentFund;
    private double remainFund;
    private boolean isActive;
    private Collection<LoanTransaction> transactions;
    private double amountToComplete;
    private Collection<Debt> debts;



    public LoanDto(Loan loan) {
        this.loanName = loan.getLoanName();
        this.borrowerName = loan.getBorrowerName();
        this.status = loan.getStatus();
        this.loanSum = loan.getLoanSum();
        this.startTimeUnit = loan.getStartTimeUnit();
        this.finishTimeUnit = loan.getFinishTimeUnit();
        this.totalTimeUnit = loan.getTotalTimeUnit();
        this.remainTimeUnit = loan.getStartTimeUnit();
        this.reason =loan.getReason() ;
        this.interestPrecent = loan.getInterestPercent();
        this.paymentFrequency = loan.getPaymentFrequency();
        this.fractions = loan.getFractions();
        this.currentInterest = loan.getCurrentInterest();
        this.remainInterest = loan.getRemainInterest();
        this.currentFund = loan.getCurrentFund();
        this.remainFund = loan.getRemainFund();
        this.isActive = loan.isActive();
        this.transactions = loan.getTransactions();
        this.amountToComplete=loan.getAmountToComplete();
        this.debts = loan.getUncompletedTransactions();
    }

    public LoanDto(LoanDto_json loanDto_json) {
        this.loanName = loanDto_json.loanName;
        this.borrowerName = loanDto_json.borrowerName;
        this.status = Status.valueOf(loanDto_json.status);
        this.loanSum = loanDto_json.loanSum;
        this.startTimeUnit = loanDto_json.startTimeUnit;
        this.totalTimeUnit = loanDto_json.totalTimeUnit;
        this.remainTimeUnit = loanDto_json.remainTimeUnit;
        this.finishTimeUnit = loanDto_json.finishTimeUnit;
        this.reason = loanDto_json.reason;
        this.interestPrecent = loanDto_json.interestPrecent;
        this.paymentFrequency = loanDto_json.paymentFrequency;
        ArrayList<Fraction> copyFractions = new ArrayList<>();
        for(Fraction_json fraction_json: loanDto_json.fractions){
            copyFractions.add(new Fraction(fraction_json));
        }
        this.fractions = copyFractions;
        this.currentInterest = loanDto_json.currentInterest;
        this.remainInterest = loanDto_json.remainInterest;
        this.currentFund = loanDto_json.currentFund;
        this.remainFund = loanDto_json.remainFund;
        this.isActive = loanDto_json.isActive;
        ArrayList<LoanTransaction> copyLoanTransactions = new ArrayList<>();
        for(LoanTransaction_json loanTransaction_json: loanDto_json.transactions){
            copyLoanTransactions.add(new LoanTransaction(loanTransaction_json));
        }
        this.transactions = copyLoanTransactions;
        this.amountToComplete = loanDto_json.amountToComplete;
        ArrayList<Debt> copyDebts = new ArrayList<>();
        for(Debt_json debt_json: loanDto_json.debts){
            copyDebts.add(new Debt(debt_json));
        }
        this.debts = copyDebts;
    }

    public int checkNextPayment() {
        int nextPayment=0;
        if(Bank.getGlobalTimeUnit() == startTimeUnit){
            nextPayment = Bank.getGlobalTimeUnit() + paymentFrequency;
        }
        else {
            if ((Bank.getGlobalTimeUnit() - startTimeUnit) % paymentFrequency == 0 || paymentFrequency == 1) {
                nextPayment = Bank.getGlobalTimeUnit() + paymentFrequency;
            } else {
                nextPayment = ((Bank.getGlobalTimeUnit() - startTimeUnit) % paymentFrequency) + Bank.getGlobalTimeUnit() + 1;
            }
        }
        return nextPayment;
    }
    public String getLoanName() {
        return loanName;
    }

    public double getInterestPrecent() {
        return interestPrecent;
    }

    public String getBorrowerName() {
        return borrowerName;
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

    public int getRemainTimeUnit() {
        return remainTimeUnit;
    }

    public int getFinishTimeUnit() {
        return finishTimeUnit;
    }

    public String getReason() {
        return reason;
    }

    public int getInterestPercent() {
        return interestPrecent;
    }

    public int getPaymentFrequency() {
        return paymentFrequency;
    }

    public Collection<Fraction> getFractions() {
        return fractions;
    }

    public double getCurrentInterest() {
        return currentInterest;
    }

    public double getRemainInterest() {
        return remainInterest;
    }

    public double getCurrentFund() {
        return currentFund;
    }

    public double getRemainFund() {
        return remainFund;
    }

    public boolean isActive() {
        return isActive;
    }

    public Collection<LoanTransaction> getTransactions() {
        return transactions;
    }

    public double getAmountToComplete() {
        return amountToComplete;
    }

    public Collection<Debt> getDebts() {
        return debts;
    }

}
