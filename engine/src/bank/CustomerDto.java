package bank;

import java.io.Serializable;
import java.util.Collection;

public class CustomerDto implements Serializable {
    private String name;
    private double balance;
    private Collection<Transaction> transactions;
    private Collection<Loan> ingoingLoans;
    private Collection<Loan> outgoingLoans;
    private int numOfPendingIngoingLoans=0;
    private int numOfActiveIngoingLoans=0;
    private int numOfRiskIngoingLoans=0;
    private int numOfFinishIngoingLoans=0;
    private int numOfPendingOutgoingLoans=0;
    private int numOfActiveOutgoingLoans=0;
    private int numOfRiskOutgoingLoans=0;
    private int numOfFinishOutgoingLoans=0;

    public CustomerDto(Customer customer) {
        this.name = customer.getName();
        this.balance = customer.getBalance();
        this.transactions = customer.getTransactions();
        this.ingoingLoans = customer.getIngoingLoans();
        this.outgoingLoans = customer.getOutgoingLoans();
        for(Loan loan:this.ingoingLoans){
            if(Status.Pending == loan.getStatus()){
                numOfPendingIngoingLoans++;
            }
            if(Status.Active == loan.getStatus()){
                numOfActiveIngoingLoans++;
            }
            if(Status.Risk == loan.getStatus()){
                numOfRiskIngoingLoans++;
            }
            if(Status.Finished == loan.getStatus()){
                numOfFinishIngoingLoans++;
            }
        }
        for(Loan loan:this.outgoingLoans){
            if(Status.Pending == loan.getStatus()){
                numOfPendingOutgoingLoans++;
            }
            if(Status.Active == loan.getStatus()){
                numOfActiveOutgoingLoans++;
            }
            if(Status.Risk == loan.getStatus()){
                numOfRiskOutgoingLoans++;
            }
            if(Status.Finished == loan.getStatus()){
                numOfFinishOutgoingLoans++;
            }
        }
    }

    public int getNumOfPendingIngoingLoans() {
        return numOfPendingIngoingLoans;
    }

    public int getNumOfActiveIngoingLoans() {
        return numOfActiveIngoingLoans;
    }

    public int getNumOfRiskIngoingLoans() {
        return numOfRiskIngoingLoans;
    }

    public int getNumOfFinishIngoingLoans() {
        return numOfFinishIngoingLoans;
    }

    public int getNumOfPendingOutgoingLoans() {
        return numOfPendingOutgoingLoans;
    }

    public int getNumOfActiveOutgoingLoans() {
        return numOfActiveOutgoingLoans;
    }

    public int getNumOfRiskOutgoingLoans() {
        return numOfRiskOutgoingLoans;
    }

    public int getNumOfFinishOutgoingLoans() {
        return numOfFinishOutgoingLoans;
    }

    public String getCustomerLoansInfo(Collection<Loan> loans){
        String res = "";

        for(Loan loan: loans){
            res += "Loan name: " + loan.getLoanName() +
                    ", Category: " + loan.getReason() +
                    ", Fund: " + loan.getLoanSum() +
                    ", Payment frequency: " + loan.getPaymentFrequency() +
                    ", Interest: " + (int)(loan.getInterestPercent()*100) + "%" +
                    ", Final loan's amount: " + loan.getStartLoanAmount() +
                    ", Status: " + loan.getStatus();
            if(loan.getStatus() == Status.Pending){
                res += ", Amount missing become 'Active': " + loan.getAmountToComplete();
            }
            if(loan.getStatus() == Status.Active) {
                res += ", Next payment at: " + loan.getNextPayment();

                res += ", sum to pay: " + loan.getNextPaymentValue();
            }
            if(loan.getStatus() == Status.Risk){
                res += ", Number of debts: " + loan.getNumOfDebts();

                res += ", amount to pay: " + loan.getSumOfDebts();
            }
            if(loan.getStatus() == Status.Finished){
                res += ", Start time: " + loan.getStartTimeUnit()
                        + ", Finish time: " + loan.getFinishTimeUnit();
            }
            res+="\n";
        }
        return res;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public Collection<Transaction> getTransactions() {
        return transactions;
    }

    public Collection<Loan> getIngoingLoans() {
        return ingoingLoans;
    }

    public Collection<Loan> getOutgoingLoans() {
        return outgoingLoans;
    }

}
