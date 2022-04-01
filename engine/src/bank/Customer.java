package bank;

import bank.exception.NegativeBalanceException;

import java.util.ArrayList;
import java.util.Collection;

public class Customer {
    private final String name;
    private double balance;
    private Collection transactions;
    private Collection ingoingLoans;
    private Collection outgoingLoans;
    private CustomerDto customerDto;

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.transactions = new ArrayList<>();
        this.ingoingLoans = new ArrayList<>();
        this.outgoingLoans = new ArrayList<>();
        this.customerDto = new CustomerDto(this);
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

    public Loan createLoan(String loanName, double loanSum, int totalTimeUnit, Reason reason, double interestPrecent, int paymentFrequency){
        Loan newLoan = new Loan( loanName, this,  loanSum,  totalTimeUnit,  reason,  interestPrecent,  paymentFrequency);
        outgoingLoans.add(newLoan);
        updateCustomerDto();
        return newLoan;
    }
    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public Collection getTransactions() {
        return transactions;
    }

    public Collection getIngoingLoans() {
        return ingoingLoans;
    }

    public Collection getOutgoingLoans() {
        return outgoingLoans;
    }

    public void updateCustomerDto() {
        this.customerDto = new CustomerDto(this);
    }

    public CustomerDto getCustomerDto() {
        return this.customerDto;
    }

    public void depositToAccount(int amount){
        try {
            Transaction deposit = new Transaction(Bank.getGlobalTimeUnit(),this,this,amount);
            this.addTransaction(deposit);
            updateCustomerDto();
        } catch (NegativeBalanceException e) { //can't be negative balance, because it's a self deposit.
        }
    }
}

