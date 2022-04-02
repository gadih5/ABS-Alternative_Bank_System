package bank;

import bank.exception.*;
import bank.xml.generated.AbsDescriptor;
import bank.xml.generated.*;

import java.util.ArrayList;
import java.util.Collection;

public class Bank {
    protected static int globalTimeUnit = 1;
    private Collection<Loan> loans = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();

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

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void addLoan(Customer customer, double loanSum, int totalTimeUnit, String reason, double interestPercent, int paymentFrequency) throws UndefinedReasonException, NegativeLoanSumException, NegativeTotalTimeUnitException, NegativeInterestPercentException, NegativePaymentFrequencyException, OverPaymentFrequencyException {
        Loan newLoan = customer.createLoan(customer.getName(), loanSum, totalTimeUnit, reason, interestPercent, paymentFrequency);
        loans.add(newLoan);
    }

    public void addCostumer(String name, double balance) throws NegativeBalanceException {
        Customer newCustomer = new Customer(name, balance);
        customers.add(newCustomer);
    }

    public Collection<LoanDto> getLoansDto() {
        Collection<LoanDto> loansDto = new ArrayList<>();
        for (Loan loan : loans) {
            loansDto.add(loan.getLoanDto());
        }
        return loansDto;
    }

    public Collection<CustomerDto> getCustomersDto() {
        Collection<CustomerDto> customersDto = new ArrayList<>();
        for (Customer customer : customers) {
            customersDto.add(customer.getCustomerDto());
        }
        return customersDto;
    }

    public void loadXmlData(AbsDescriptor descriptor) throws NegativeBalanceException, UndefinedReasonException, UndefinedCustomerException, NegativeLoanSumException, NegativeTotalTimeUnitException, NegativeInterestPercentException, NegativePaymentFrequencyException, OverPaymentFrequencyException {
       AbsCustomers absCustomers = descriptor.getAbsCustomers();
       for (AbsCustomer absCustomer: absCustomers.getAbsCustomer()){
           this.customers.add(new Customer(absCustomer.getName(), absCustomer.getAbsBalance()));
       }

       AbsLoans absLoans = descriptor.getAbsLoans();
        for(AbsLoan absLoan: absLoans.getAbsLoan()) {
            Customer loanCustomer = null;
            for(Customer customer: customers){
                if(customer.getName().equals(absLoan.getAbsOwner())){
                    loanCustomer = customer;
                    break;
                }
            }
            if(loanCustomer == null){
                throw new UndefinedCustomerException(absLoan.getAbsOwner() + "is not bank's customer!");
            }
            this.loans.add(new Loan(absLoan.getId(),loanCustomer,absLoan.getAbsCapital(),absLoan.getAbsTotalYazTime(),absLoan.getAbsCategory(),
                    ((double)absLoan.getAbsIntristPerPayment()*((double)absLoan.getAbsTotalYazTime()/(double)absLoan.getAbsPaysEveryYaz()))/(double)absLoan.getAbsCapital(),absLoan.getAbsPaysEveryYaz()));
        }
    }
}
