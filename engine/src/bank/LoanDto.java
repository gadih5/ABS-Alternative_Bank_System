package bank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class LoanDto implements Serializable, Comparable<LoanDto> {
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
    private double currentInterest;
    private double remainInterest;
    private double currentFund;
    private double remainFund;
    private boolean isActive;
    private Collection<LoanTransaction> transactions;
    private double amountToComplete;
    private ArrayList<String> loaners;
    private int nextPaymentYaz;
    private double nextPaymentValue;

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
        this.loaners = loan.getLoanersNames();
        this.currentInterest = loan.getCurrentInterest();
        this.remainInterest = loan.getRemainInterest();
        this.currentFund = loan.getCurrentFund();
        this.remainFund = loan.getRemainFund();
        this.isActive = loan.isActive();
        this.transactions = loan.getTransactions();
        this.amountToComplete=loan.getAmountToComplete();
        this.nextPaymentYaz = loan.getNextPayment();
        this.nextPaymentValue = loan.getNextPaymentValue();
    }

    public ArrayList<String> getLoaners() {
        return loaners;
    }

    public String getLoanName() {
        return loanName;
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

    public int getTotalTimeUnit() {
        return totalTimeUnit;
    }

    public int getRemainTimeUnit() {
        return remainTimeUnit;
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

    public boolean isActive() {
        return isActive;
    }

    public Collection<LoanTransaction> getTransactions() {
        return transactions;
    }

    public double getAmountToComplete() {
        return amountToComplete;
    }

    @Override
    public int compareTo(LoanDto o) {
        return (loanName.compareTo(((LoanDto) o).getLoanName()));
    }
}