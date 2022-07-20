package bank;

import _json.Customer_json;
import _json.Loan_json;
import _json.PreTransaction_json;
import bank.exception.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Customer implements Serializable {
    private final String name;
    private double balance;
    private ArrayList<Transaction> transactions;
    private ArrayList<Loan> ingoingLoans;
    private ArrayList<Loan> outgoingLoans;
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

    /*public Customer(Customer_json customer_json) {
        this.name = customer_json.name;
        this.balance = customer_json.balance;
        this.transactions = customer_json.transactions;
        ArrayList<Loan> copyIngoingLoans = new ArrayList<>();
        for(Loan_json loan_json: customer_json.ingoingLoans){
            copyIngoingLoans.add(new Loan(loan_json));
        }
        this.ingoingLoans = copyIngoingLoans;
        ArrayList<Loan> copyOutgoingLoans = new ArrayList<>();
        for(Loan_json loan_json: customer_json.outgoingLoans){
            copyOutgoingLoans.add(new Loan(loan_json));
        }
        this.outgoingLoans = copyOutgoingLoans;
        this.customerDto = new CustomerDto(customer_json.customerDto);
        ArrayList<PreTransaction> copyPreTransactions = new ArrayList<>();
        for(PreTransaction_json preTransaction_json: customer_json.preTransactions){
            copyPreTransactions.add(new PreTransaction(preTransaction_json));
        }
        this.preTransactions = copyPreTransactions;
        this.isAdmin = customer_json.isAdmin;
    }*/

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

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public ArrayList<LoanDto> getIngoingLoans() {
        ArrayList<LoanDto> list = new ArrayList<>();
        for (Loan loan: ingoingLoans){
            list.add(loan.getLoanDto());
        }
        return list;
    }

    public ArrayList<LoanDto>getOutgoingLoans() {
        ArrayList<LoanDto> list = new ArrayList<>();
        for (Loan loan: outgoingLoans){
            list.add(loan.getLoanDto());
        }
        return list;
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

    public void addOutgoingLoan(Loan loan) {
        outgoingLoans.add(loan);
        updateCustomerDto();
    }

    public void addPreTransaction(PreTransaction preTransaction) {
        preTransactions.add(preTransaction);
    }


    public void clearAllPreTransactions(Loan selectedLoan) {
        ArrayList<PreTransaction> preTransactionsToRemove = new ArrayList<>();
        for(PreTransaction preTransaction: preTransactions){
            if(preTransaction.getLoan() == selectedLoan){
                preTransactionsToRemove.add(preTransaction);
            }
        }
        preTransactions.removeAll(preTransactionsToRemove);
    }

    public void makeAllPreTransactionsPaid(LoanDto selectedLoan) {
        for(PreTransaction preTransaction: preTransactions){
            if(preTransaction.getLoan().getLoanDto() == selectedLoan){
                preTransaction.setPaid(true);
            }
        }
    }
}

