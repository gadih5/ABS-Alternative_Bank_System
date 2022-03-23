package bank;

import bank.exception.NegativeBalanceException;
import bank.Transaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import bank.Bank;
public class Customer {
    private String name;
    private double balance;
    private Collection transactions;
    private Collection ingoingLoans;
    private Collection outgoingLoans;

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.transactions = new ArrayList<>();
        this.ingoingLoans = new ArrayList<>();
        this.outgoingLoans = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public void setBalance(double balance) throws NegativeBalanceException {
        if(balance < 0)
            throw new NegativeBalanceException("Negative balance");
        else
            this.balance = balance;
    }

    public Loan createLoan(String loanName, double loanSum, int totalTimeUnit, Type reason, double interestPrecent, int paymentFrequency){
        Loan newLoan = new Loan( loanName, this,  loanSum,  totalTimeUnit,  reason,  interestPrecent,  paymentFrequency);
        outgoingLoans.add(newLoan);
        return newLoan;
    }
    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    private Collection getTransactions() {
        return transactions;
    }

    private Collection getIngoingLoans() {
        return ingoingLoans;
    }

    private Collection getOutgoingLoans() {
        return outgoingLoans;
    }

}

