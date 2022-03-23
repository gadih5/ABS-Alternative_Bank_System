package bank;

import bank.exception.NegativeBalanceException;

import java.util.ArrayList;
import java.util.Collection;
public class Bank {
    private int globalTimeUnit = 1;
    private Collection<Loan> loans = new ArrayList<>();
    private Collection customers = new ArrayList<Customer>();

    public int getGlobalTimeUnit() {
        return globalTimeUnit;
    }

    public void setGlobalTimeUnit() throws NegativeBalanceException {
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

    public void addLoan(Customer customer, double loanSum, int totalTimeUnit, Type reason, double interestPrecent, int paymentFrequency){
        Loan newLoan = customer.createLoan(customer.getName(),loanSum, totalTimeUnit, reason, interestPrecent,paymentFrequency);
        loans.add(newLoan);
    }

    public void addCostumer(String name, double balance){
        customers.add(new Customer(name,balance));
    }

}
