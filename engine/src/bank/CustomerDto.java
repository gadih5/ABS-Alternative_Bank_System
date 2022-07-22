package bank;

import _json.*;
import javafx.application.Platform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class CustomerDto implements Serializable {
    private String name;
    private double balance;
    //private Collection<TransactionDto> transactions;
    private ArrayList<LoanDto> ingoingLoans;
    private ArrayList<LoanDto> outgoingLoans;
    private Collection<PreTransaction> preTransactions;
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
      //  this.transactions = customer.getTransactions();
        this.ingoingLoans = customer.getIngoingLoans();
        this.outgoingLoans = customer.getOutgoingLoans();
        this.preTransactions = customer.getPreTransactions();
        numOfPendingIngoingLoans=0;
        numOfActiveIngoingLoans=0;
        numOfRiskIngoingLoans=0;
        numOfFinishIngoingLoans=0;
        numOfPendingOutgoingLoans=0;
        numOfActiveOutgoingLoans=0;
        numOfRiskOutgoingLoans=0;
        numOfFinishOutgoingLoans=0;
        for(LoanDto loan:this.ingoingLoans){
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
        for(LoanDto loan:this.outgoingLoans){
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



    /*public  CustomerDto(CustomerDto_json customerDto_json) {
        this.name = customerDto_json.name;
        this.balance = customerDto_json.balance;
        ArrayList<Transaction> copyTransactions = new ArrayList<>();
        for(Transaction_json transaction_json: customerDto_json.transactions){
            copyTransactions.add(new Transaction(transaction_json));
        }
        this.transactions = copyTransactions;
        ArrayList<Loan> copyIngoingLoans = new ArrayList<>();
        for(Loan_json loan_json: customerDto_json.ingoingLoans){
            copyIngoingLoans.add(new Loan(loan_json));
        }
        this.ingoingLoans = copyIngoingLoans;
        ArrayList<Loan> copyOutgoingLoans = new ArrayList<>();
        for(Loan_json loan_json: customerDto_json.outgoingLoans){
            copyOutgoingLoans.add(new Loan(loan_json));
        }
        this.outgoingLoans = copyOutgoingLoans;
        ArrayList<PreTransaction> copyPreTransactions = new ArrayList<>();
        for(PreTransaction_json preTransaction_json: customerDto_json.preTransactions){
            copyPreTransactions.add(new PreTransaction(preTransaction_json));
        }
        this.preTransactions = copyPreTransactions;
        this.numOfPendingIngoingLoans = customerDto_json.numOfPendingIngoingLoans;
        this.numOfActiveIngoingLoans = customerDto_json.numOfActiveIngoingLoans;
        this.numOfRiskIngoingLoans = customerDto_json.numOfRiskIngoingLoans;
        this.numOfFinishIngoingLoans = customerDto_json.numOfFinishIngoingLoans;
        this.numOfPendingOutgoingLoans = customerDto_json.numOfPendingOutgoingLoans;
        this.numOfActiveOutgoingLoans = customerDto_json.numOfActiveOutgoingLoans;
        this.numOfRiskOutgoingLoans = customerDto_json.numOfRiskOutgoingLoans;
        this.numOfFinishOutgoingLoans = customerDto_json.numOfFinishOutgoingLoans;
    }*/

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

   // public Collection<Transaction> getTransactions() {
  //      return transactions;
  //  }

    public ArrayList<LoanDto> getIngoingLoans() {
        return ingoingLoans;
    }

    public ArrayList<LoanDto> getOutgoingLoans() {
        return outgoingLoans;
    }

    public Collection<PreTransaction> getPreTransactions() {
        return preTransactions;
    }
}
