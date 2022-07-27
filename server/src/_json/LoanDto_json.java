package _json;

import java.util.Collection;

public class LoanDto_json {
    public String loanName;
    public String borrowerName;
    public String status;
    public double loanSum;
    public int startTimeUnit;
    public int totalTimeUnit;
    public int remainTimeUnit;
    public int finishTimeUnit;
    public String reason;
    public int interestPrecent;
    public int paymentFrequency;
    public Collection<Fraction_json> fractions;
    public double currentInterest;
    public double remainInterest;
    public double currentFund;
    public double remainFund;
    public boolean isActive;
    public Collection<LoanTransaction_json> transactions;
    public double amountToComplete;
    public Collection<Debt_json> debts;
}