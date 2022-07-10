package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class GsonModel {

    private static class LoanTransaction{
        private Transaction transaction;
        private double fundPart;
        private double interestPart;
    }

    private static class Fraction{
        private int convertTime;
        private Customer customer;
        private double amount;
    }

    private static class PreTransaction {
        private bank.Loan loan;
        private String loanName;
        private Customer toCustomer;
        private int payTime;
        private double fundPart;
        private double interestPart;
        private double sum;
        private boolean paid;
    }
        private static class Transaction{
        private Customer toCustomer;
        private double amount;
        private int timeUnit;
        private double previousBalance;
        private double afterBalance;
        private String sign;
    }

    private static class Debt{
        private bank.Customer toCustomer;
        private double fundPart;
        private double interestPart;
        private double amount;
    }

    private static class LoanDto{
        private String loanName;
        private String borrowerName;
        private String status;
        private double loanSum;
        private int startTimeUnit;
        private int totalTimeUnit;
        private int remainTimeUnit;
        private int finishTimeUnit;
        private String reason;
        private int interestPrecent;
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
    }

    private static class CustomerDto{
        private String name;
        private double balance;
        private Collection<Transaction> transactions;
        private Collection<bank.Loan> ingoingLoans;
        private Collection<bank.Loan> outgoingLoans;
        private Collection<PreTransaction> preTransactions;
        private int numOfPendingIngoingLoans;
        private int numOfActiveIngoingLoans;
        private int numOfRiskIngoingLoans;
        private int numOfFinishIngoingLoans;
        private int numOfPendingOutgoingLoans;
        private int numOfActiveOutgoingLoans;
        private int numOfRiskOutgoingLoans;
        private int numOfFinishOutgoingLoans;
    }

    private static class Customer{
        private  String name;
        private double balance;
        private ArrayList transactions;
        private ArrayList ingoingLoans;
        private ArrayList<Loan> outgoingLoans;
        private CustomerDto customerDto;
        private ArrayList<PreTransaction> preTransactions;
        private Boolean isAdmin;
    }
    private static class Loan{
        private double startLoanAmount;
        private String loanName;
        private Customer borrower;
        private String status;
        private double loanSum;
        private int startTimeUnit;
        private int totalTimeUnit;
        private int remainTimeUnit;
        private int finishTimeUnit;
        private String reason;
        private int interestPercent;
        private int paymentFrequency;
        private ArrayList<Fraction> fractions;
        private double currentInterest;
        private double remainInterest;
        private double currentFund;
        private double remainFund;
        private boolean isActive;
        private ArrayList transactions;
        private double amountToComplete;
        private ArrayList<Debt> uncompletedTransactions;
        private LoanDto loanDto;
        private int nextPayment;
        private double nextPaymentValue;
        private int numOfDebts;
        private double sumOfDebts;
    }

    private static class Bank{
        private ArrayList<Loan> loans;
        private ArrayList<Customer> customers;
        private Set<String> category;
    }
}
