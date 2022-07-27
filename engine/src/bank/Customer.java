package bank;

import bank.exception.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Customer implements Serializable {
    private final String name;
    private double balance;
    private ArrayList<Transaction> transactions;
    private ArrayList<LoanDto> ingoingLoans;
    private ArrayList<LoanDto> outgoingLoans;
    private CustomerDto customerDto;
    private ArrayList<PreTransaction> preTransactions;
    private Boolean isAdmin;

    public Customer(String name, double balance, Boolean isAdmin) throws NegativeBalanceException {
        this.name = name;
        this.setBalance(balance);
        this.transactions = new ArrayList<>();
        this.ingoingLoans = new ArrayList<>();
        this.outgoingLoans = new ArrayList<>();
        this.customerDto = new CustomerDto(this);
        this.preTransactions = new ArrayList<>();
        this.isAdmin = isAdmin;
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

    public void addIngoingLoan(Loan loan,double amount) throws NegativeBalanceException {
        ingoingLoans.add(loan.getLoanDto());
        Transaction newTransaction = new Transaction(this,loan.getBorrower(),amount);
        addTransaction(newTransaction);
        updateCustomerDto();
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public ArrayList<LoanDto> getIngoingLoans() {
        return ingoingLoans;
    }

    public ArrayList<LoanDto>getOutgoingLoans() {
        return outgoingLoans;
    }

    public Collection<PreTransaction> getPreTransactions() {
        return preTransactions;
    }

    public void updateCustomerDto() {
        this.customerDto = new CustomerDto(this);
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public CustomerDto getCustomerDto() {
        return this.customerDto;
    }

    public void selfTransaction(int amount) throws NegativeBalanceException {
        Transaction transaction = new Transaction(this, this, amount);
        this.addTransaction(transaction);
        updateCustomerDto();
    }

    public void addPreTransaction(PreTransaction preTransaction) {
        preTransactions.add(preTransaction);
    }

    public void makeAllPreTransactionsPaid(LoanDto selectedLoan) {
        for(PreTransaction preTransaction: preTransactions){
            if(preTransaction.getLoan() == selectedLoan){
                preTransaction.setPaid(true);
            }
        }
    }
}