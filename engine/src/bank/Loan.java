package bank;

import java.util.ArrayList;
import java.util.Collection;

public class Loan {
    private String loanName;
    private String borrowerName;
    private Status status;
    private double loanSum;
    private int startTimeUnit;
    private int totalTimeUnit;
    private int remainTimeUnit;
    private Type reason;
    private double interestPrecent;
    private int paymentFrequency;
    private Collection loaners;
    private double currentInterest;
    private double remainInterest;
    private double currentFund;
    private double remainFund;
    private boolean isActive;
    

    public Loan(String loanName, String borrowerName, double loanSum, int totalTimeUnit, Type reason, double interestPrecent, int paymentFrequency) {
        this.loanName = loanName;
        this.borrowerName = borrowerName;
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
        this.loaners = new ArrayList<Customer>();
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

    private void addLoaner(Customer loaner){
        loaners.add(loaner);
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

    private Collection getLoaners() {
        return loaners;
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

    public void update(int globalTimeUnit) {
        this.remainTimeUnit--;
        if(this.isActive && remainTimeUnit >= 0 && ((globalTimeUnit - startTimeUnit)%paymentFrequency==0 || paymentFrequency == 1))
        {
            for()
        }
    }
}
