package bank;

import java.util.ArrayList;
import java.util.Collection;
public class Bank {
    private int globalTimeUnit = 1;
    private Collection<Loan> loans = new ArrayList<>();
    private Collection customers = new ArrayList<Customer>();

    public int getGlobalTimeUnit() {
        return globalTimeUnit;
    }

    public void setGlobalTimeUnit() {
        this.globalTimeUnit++;
        for(Loan loan: loans){
            loan.update(globalTimeUnit);
        }
    }

    public Collection getLoans() {
        return loans;
    }

    public Collection getCustomers() {
        return customers;
    }

    public void addLoan(String loanName, String borrowerName, double loanSum, int totalTimeUnit, Type reason, double interestPrecent, int paymentFrequency){
        loans.add(new Loan(loanName,  borrowerName,  loanSum,  totalTimeUnit,  reason,  interestPrecent,  paymentFrequency));
    }

    public void addCostumer(String name, double balance){
        customers.add(new Customer(name,balance));
    }

}
