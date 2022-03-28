package bank;

import java.util.Collection;

public class LoanDto {
    private String loanName;
    private String borrowerName;
    private Status status;
    private double loanSum;
    private int startTimeUnit;
    private int totalTimeUnit;
    private int remainTimeUnit;
    private int finishTimeUnit;
    private Type reason;
    private double interestPrecent;
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


    @Override
    public String toString() {
        String res=    "Loan Name: "+ loanName +
                ", Borrower Name: " + borrowerName +
                ", Reason: " + reason +
                ", Loan Sum: " + loanSum +
                ", Total Loan Time: " + totalTimeUnit +
                ", interest: " + interestPrecent +
                ", Payment Frequency: " + paymentFrequency +
                ", Status: " + status+"\n\r";
                String frqString="Loaners: \n";
                for(Fraction frq:fractions){
                frqString +=frq.toString();
                  }
                res+=frqString;
        if(status==Status.Pending){
            res+="\nThe total amount that being collect so far";
            res+="\nThe remaining amount to activate the loan: "+amountToComplete;

        }else{
        int nextPayment = checkNextPayment();
        String activeStr="Become Active at: " + startTimeUnit + "Next Payment in: "+nextPayment;
        activeStr+="/nThe Payments maid so far:\n";

        for(LoanTransaction transaction:transactions){
            activeStr+="Time unit:"+transaction.getTimeUnit()+" Fund:"+transaction.getFundPart()+" Interest:"+transaction.getInterestPart()+" Total: "+(transaction.getInterestPart()+transaction.getFundPart())+"\n";
        }
        activeStr+="Total fund paid:"+currentFund+" Total interest paid:"+currentInterest+" Total remain fund:"+ remainFund+" Total remain interest:" +remainInterest;
        res+=activeStr;
        if(status==Status.Risk){
            String debtsInfo = this.borrowerName + "debts: ";
            double debtAmount = 0;
            int numOfDebts = 0;
                    for(Debt debt: debts){
                        debtsInfo += debt.toString() + "\n";
                        debtAmount += debt.getAmount();
                        numOfDebts++;
                    }
                    debtsInfo += "Number of debts: " + numOfDebts
                                +", Sum of debts: " + debtAmount + "\n";
                    res += debtsInfo;
        }
        if(status==Status.Finished) {
            String finishInfo = "Start time: " + this.startTimeUnit
                              + ", Finish time: " + this.finishTimeUnit + "\n";
            res += finishInfo;
        }
        }
        return res;
    }

    public int checkNextPayment() {
        int nextPayment;
        if((Bank.getGlobalTimeUnit() - startTimeUnit)%paymentFrequency==0 || paymentFrequency == 1){
            nextPayment = Bank.getGlobalTimeUnit();
        }
        if(Bank.getGlobalTimeUnit() == startTimeUnit){
            nextPayment = Bank.getGlobalTimeUnit() + paymentFrequency;
        }
        else{
            nextPayment = (Bank.getGlobalTimeUnit() - startTimeUnit)%paymentFrequency + Bank.getGlobalTimeUnit();
        }
        return nextPayment;
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

    public Type getReason() {
        return reason;
    }

    public double getInterestPrecent() {
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

    public LoanDto(Loan loan) {
        this.loanName = loan.getSerialNumber();
        this.borrowerName = loan.getBorrowerName();
        this.status = loan.getStatus();
        this.loanSum = loan.getLoanSum();
        this.startTimeUnit = loan.getStartTimeUnit();
        this.finishTimeUnit = loan.getFinishTimeUnit();
        this.totalTimeUnit = loan.getTotalTimeUnit();
        this.remainTimeUnit = loan.getStartTimeUnit();
        this.reason =loan.getReason() ;
        this.interestPrecent = loan.getInterestPrecent();
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
}
