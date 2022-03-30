package bank;

import bank.exception.NegativeBalanceException;

import java.util.ArrayList;
import java.util.Collection;
public class Bank {
    protected static int globalTimeUnit = 1;
    private Collection<Loan> loans = new ArrayList<>();
    private Collection<Customer> customers = new ArrayList<>();

    public static int getGlobalTimeUnit() {
        return globalTimeUnit;
    }

    public static void setGlobalTimeUnit(int globalTimeUnit) {
        Bank.globalTimeUnit = globalTimeUnit;
    }

    public void setGlobalTimeUnit() throws NegativeBalanceException {
        int temp = getGlobalTimeUnit();
        setGlobalTimeUnit(temp++);
        for (Loan loan : loans) {
            loan.update(globalTimeUnit);
        }
        for (Customer customer : customers) {
            customer.updateCustomerDto();
        }
    }

    public Collection getLoans() {
        return loans;
    }

    public Collection getCustomers() {
        return customers;
    }

    public void addLoan(Customer customer, double loanSum, int totalTimeUnit, Type reason, double interestPercent, int paymentFrequency) {
        Loan newLoan = customer.createLoan(customer.getName(), loanSum, totalTimeUnit, reason, interestPercent, paymentFrequency);
        loans.add(newLoan);
    }

    public void addCostumer(String name, double balance) {
        Customer newCustomer = new Customer(name, balance);
        customers.add(newCustomer);
    }


    public Collection<LoanDto> getLoansDto() {
        Collection<LoanDto> loansDto = null;
        for (Loan loan : loans) {
            loansDto.add(loan.getLoanDto());
        }
        return loansDto;
    }

    public Collection<CustomerDto> getCustomersDto() {
        Collection<CustomerDto> customersDto = null;
        for (Customer customer : customers) {
            customersDto.add(customer.getCustomerDto());
        }
        return customersDto;
    }
}
