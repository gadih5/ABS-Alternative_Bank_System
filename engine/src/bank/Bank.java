package bank;

import bank.exception.NegativeBalanceException;

import java.util.ArrayList;
import java.util.Collection;
public class Bank {
    protected static  int globalTimeUnit = 1;
    private Collection<Loan> loans = new ArrayList<>();
    private Collection<LoanDto> loansDto = new ArrayList<>();

    private Collection customers = new ArrayList<Customer>();

    public static int getGlobalTimeUnit() {
        return globalTimeUnit;
    }

    public static void setGlobalTimeUnit(int globalTimeUnit) {
        Bank.globalTimeUnit = globalTimeUnit;
    }

    public void setGlobalTimeUnit() throws NegativeBalanceException {
        int temp =getGlobalTimeUnit();
        setGlobalTimeUnit(temp++);
        for(Loan loan: loans){
            loan.update(globalTimeUnit);
            updateLoansDto();
        }
    }

    private void updateLoansDto() {

    }

    public Collection getLoans() {
        return loans;
    }

    public Collection getCustomers() {
        return customers;
    }

    public Collection<LoanDto> getLoansDto() {
        return loansDto;
    }

    public void addLoan(Customer customer, double loanSum, int totalTimeUnit, Type reason, double interestPrecent, int paymentFrequency){
        Loan newLoan = customer.createLoan(customer.getName(),loanSum, totalTimeUnit, reason, interestPrecent,paymentFrequency);
        loans.add(newLoan);
    }

    public void addCostumer(String name, double balance){
        customers.add(new Customer(name,balance));
    }



}
