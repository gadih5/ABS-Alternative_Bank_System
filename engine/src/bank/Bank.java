package bank;

import bank.exception.NegativeBalanceException;

import java.util.ArrayList;
import java.util.Collection;
public class Bank {
    protected static int globalTimeUnit = 1;
    private Collection<Loan> loans = new ArrayList<>();
    private Collection<LoanDto> loansDto = new ArrayList<>();
    private Collection<Customer> customers = new ArrayList<>();
    private Collection<CustomerDto> customersDto = new ArrayList<>();

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
            for(LoanDto loanDto: loansDto){
                loanDto = new LoanDto();
            }
            for(CustomerDto customerDto: customersDto){
                customerDto.update();
            }
        }
    }

    private void updateCustomersDto() {
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
    public Collection<CustomerDto> getCustomersDto(){
        return customersDto;
    }
    public void addLoan(Customer customer, double loanSum, int totalTimeUnit, Type reason, double interestPercent, int paymentFrequency){
        Loan newLoan = customer.createLoan(customer.getName(),loanSum, totalTimeUnit, reason, interestPercent,paymentFrequency);
        loans.add(newLoan);
        loansDto.add(new LoanDto(newLoan));
    }

    public void addCostumer(String name, double balance){
        Customer newCustomer = new Customer(name,balance);
        customers.add(newCustomer);
        customersDto.add(new CustomerDto(newCustomer));
    }



}
