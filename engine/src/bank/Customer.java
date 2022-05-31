package bank;

import bank.exception.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Customer implements Serializable {
    private final String name;
    private double balance;
    private Collection transactions;
    private Collection ingoingLoans;
    private Collection<Loan> outgoingLoans;
    private CustomerDto customerDto;
    private Collection<PreTransaction> preTransactions;

    public Customer(String name, double balance) throws NegativeBalanceException {
        this.name = name;
        this.setBalance(balance);
        this.transactions = new ArrayList<>();
        this.ingoingLoans = new ArrayList<>();
        this.outgoingLoans = new ArrayList<>();
        this.customerDto = new CustomerDto(this);
        this.preTransactions = new ArrayList<>();
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
        ingoingLoans.add(loan);
        Transaction newTransaction = new Transaction(this,loan.getBorrower(),amount);
        addTransaction(newTransaction);
        updateCustomerDto();
    }

    public Loan createLoan(String loanName, double loanSum, int totalTimeUnit, String reason, int interestPrecent, int paymentFrequency) throws UndefinedReasonException, NegativeLoanSumException, NegativeTotalTimeUnitException, NegativeInterestPercentException, NegativePaymentFrequencyException, OverPaymentFrequencyException, UndividedPaymentFrequencyException {
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

    public Collection<Loan>getOutgoingLoans() {
        return outgoingLoans;
    }

    public void updateCustomerDto() {
        this.customerDto = new CustomerDto(this);
    }

    public CustomerDto getCustomerDto() {
        return this.customerDto;
    }

    public void selfTransaction(int amount) throws NegativeBalanceException {
        Transaction transaction = new Transaction(this, this, amount);
        this.addTransaction(transaction);
        updateCustomerDto();
    }

    public void addOutgoingLoan(Loan loan) {
        outgoingLoans.add(loan);
        updateCustomerDto();
    }

    public void addPreTransaction(PreTransaction preTransaction) {
        preTransactions.add(preTransaction);
    }
}

