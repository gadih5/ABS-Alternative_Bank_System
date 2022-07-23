package bank;

import _json.PreTransaction_json;
import bank.exception.NegativeBalanceException;

public class PreTransaction {
    static int idCounter = 0;
    private String id;
    private LoanDto loan;
    private String loanName;
    private CustomerDto toCustomer;
    private int payTime;
    private double fundPart;
    private double interestPart; //positive value
    private double sum;
    private boolean paid = false;

    public PreTransaction(CustomerDto toCustomer,int payTime, double fundPart, double interestPart, LoanDto loan){
        this.id = String.valueOf(idCounter++);
        this.fundPart = fundPart;
        this.payTime = payTime;
        this.interestPart = interestPart;
        this.toCustomer = toCustomer;
        this.loan = loan;
        this.loanName = loan.getLoanName();
        this.sum = fundPart + interestPart;
    }

    public LoanDto getLoan() {
        return loan;
    }

    public String getId() {
        return id;
    }

    public String getToCustomer() {
        return toCustomer.getName();
    }

    public String getLoanName() {
        return loanName;
    }

    public int getPayTime() {
        return payTime;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public double getFundPart() {
        return fundPart;
    }

    public double getSum() {
        return sum;
    }

    public double getInterestPart() {
        return interestPart;
    }

    public void makeTransaction(CustomerDto fromCustomer) throws NegativeBalanceException {
        //new Transaction(fromCustomer,toCustomer,fundPart+interestPart);
        Customer fromCustomer1 = Bank.getSpecificCustomer(fromCustomer.getName());
        Customer toCustomer1 = Bank.getSpecificCustomer(toCustomer.getName());
        fromCustomer1.addTransaction(new Transaction(fromCustomer1,toCustomer1,fundPart+interestPart));
        Loan loan1 = Bank.getSpecificLoan(loan.getLoanName());
        loan1.setRemainFund(-fundPart);
        loan1.setRemainInterest(-interestPart);
        loan1.setCurrentFund(fundPart);
        loan1.setCurrentInterest(interestPart);

        paid = true;

        loan1.updateLoanDto();
        fromCustomer1.updateCustomerDto();
        toCustomer1.updateCustomerDto();
    }

    @Override
    public String toString() {
        String res = "Loan: " + loan.getLoanName() +
                ", Pay time: " + payTime +
                ", Sum to pay: " + (fundPart+interestPart);
                if(paid)
                    res += " Paid";
                else
                    res += " Unpaid";
        return res;
    }

    public boolean isPaid() {
        return paid;
    }

}
