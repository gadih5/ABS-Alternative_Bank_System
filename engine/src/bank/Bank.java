package bank;

import bank.exception.*;
import bank.xml.generated.AbsDescriptor;
import bank.xml.generated.*;

import java.io.Serializable;
import java.util.*;

public class Bank  implements Serializable {
    protected static int globalTimeUnit = 1;
    private int syncGlobalTimeUnit = 1;
    private Collection<Loan> loans = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private Set<String> category=null;

    public Bank (){
        globalTimeUnit = 1;
    }
    public static int getGlobalTimeUnit() {
        return globalTimeUnit;
    }

    public static void setGlobalTimeUnit(int globalTimeUnit) {
        Bank.globalTimeUnit = globalTimeUnit;
    }

    public int getSyncGlobalTimeUnit() {
        return syncGlobalTimeUnit;
    }

    public void updateGlobalTimeUnit() throws NegativeBalanceException {
        int curr = getGlobalTimeUnit();
        setGlobalTimeUnit(++curr);
        syncGlobalTimeUnit++;
        for (Loan loan : loans) {
            loan.update();
        }
        for (Customer customer : customers) {
            customer.updateCustomerDto();
        }
    }

    public Set<String> getCategory() {
        return category;
    }

    public Collection<Loan> getLoans() {
        return loans;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void addLoan(Customer customer, double loanSum, int totalTimeUnit, String reason, int interestPercent, int paymentFrequency) throws UndefinedReasonException, NegativeLoanSumException, NegativeTotalTimeUnitException, NegativeInterestPercentException, NegativePaymentFrequencyException, OverPaymentFrequencyException, UndividedPaymentFrequencyException {
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

    public void loadXmlData(AbsDescriptor descriptor) throws NegativeBalanceException, UndefinedReasonException, UndefinedCustomerException, NegativeLoanSumException, NegativeTotalTimeUnitException, NegativeInterestPercentException, NegativePaymentFrequencyException, OverPaymentFrequencyException, UndividedPaymentFrequencyException, NotInCategoryException, AlreadyExistCustomerException {
       AbsCustomers absCustomers = descriptor.getAbsCustomers();
       Set<String> customerNames = new HashSet<>();
       for (AbsCustomer absCustomer: absCustomers.getAbsCustomer()){
           if(customerNames.add(absCustomer.getName()))
               this.customers.add(new Customer(absCustomer.getName(), absCustomer.getAbsBalance()));
           else
               throw new AlreadyExistCustomerException("There is already a customer named \"" + absCustomer.getName() +"\", can't be two customers with the same name!");
       }

        Set<String> listOfCategory=new HashSet<>();
        for(String s:descriptor.getAbsCategories().getAbsCategory()){
            listOfCategory.add(s);
        }
        category=listOfCategory;

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
                throw new UndefinedCustomerException("\"" + absLoan.getAbsOwner() + "\" is not bank's customer!");
            }
            if(listOfCategory.contains(absLoan.getAbsCategory())) {
                    this.loans.add(new Loan(absLoan.getId(), loanCustomer, absLoan.getAbsCapital(), absLoan.getAbsTotalYazTime(), absLoan.getAbsCategory(),
                            (int) absLoan.getAbsIntristPerPayment(), absLoan.getAbsPaysEveryYaz()));
            }else{
                throw new NotInCategoryException("\"" + absLoan.getAbsCategory() + "\" is missing in the categories list in Xml!");
            }
        }
    }

    public ArrayList<Loan> checkLoans(Customer customer, Set<String> chosenCategories, int chosenInterest, int chosenUnitTime) {
        ArrayList<Loan> possibleLoans = new ArrayList<>();
        for(Loan loan: loans){
            if(loan.getStatus() == Status.Pending
                    && loan.getBorrower() != customer
                    && chosenCategories.contains(loan.getReason())
                    && loan.getInterestPercent()*100 >= chosenInterest
                    && loan.getTotalTimeUnit() >= chosenUnitTime){
                possibleLoans.add(loan);
            }
        }
        return possibleLoans;
    }

    public ArrayList<LoanDto> makeDto(ArrayList<Loan> possibleLoans) {
        ArrayList<LoanDto> loansDto = new ArrayList<>();
        for (Loan loan: possibleLoans){
            LoanDto loanDto = new LoanDto(loan);
            loansDto.add(loanDto);
        }
        return loansDto;
    }
}
