package bank;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomerDto implements Serializable {
    private String name;
    private double balance;
    private ArrayList<LoanDto> ingoingLoans;
    private ArrayList<LoanDto> outgoingLoans;
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
        this.ingoingLoans = customer.getIngoingLoans();
        this.outgoingLoans = customer.getOutgoingLoans();
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

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }
}