package _json;

import java.util.ArrayList;

public class Loan_json {
    public double startLoanAmount;
    public String loanName;
    public Customer_json borrower;
    public static String status;
    public double loanSum;
    public int startTimeUnit;
    public int totalTimeUnit;
    public int remainTimeUnit;
    public int finishTimeUnit;
    public String reason;
    public int interestPercent;
    public int paymentFrequency;
    public ArrayList<Fraction_json> fractions;
    public double currentInterest;
    public double remainInterest;
    public double currentFund;
    public double remainFund;
    public boolean isActive;
    public ArrayList<Transaction_json> transactions;
    public double amountToComplete;
    public ArrayList<Debt_json> uncompletedTransactions;
    public LoanDto_json loanDto;
    public int nextPayment;
    public double nextPaymentValue;
    public int numOfDebts;
    public double sumOfDebts;
}