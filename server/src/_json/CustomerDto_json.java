package _json;

import java.util.Collection;

public class CustomerDto_json {
    public String name;
    public double balance;
    public Collection<Transaction_json> transactions;
    public Collection<Loan_json> ingoingLoans;
    public Collection<Loan_json> outgoingLoans;
    public Collection<PreTransaction_json> preTransactions;
    public int numOfPendingIngoingLoans;
    public int numOfActiveIngoingLoans;
    public int numOfRiskIngoingLoans;
    public int numOfFinishIngoingLoans;
    public int numOfPendingOutgoingLoans;
    public int numOfActiveOutgoingLoans;
    public int numOfRiskOutgoingLoans;
    public int numOfFinishOutgoingLoans;
}
