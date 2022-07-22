package bank;

import bank.exception.*;
import bank.xml.generated.AbsDescriptor;
import bank.xml.generated.AbsLoan;
import bank.xml.generated.AbsLoans;

import java.io.Serializable;
import java.util.*;

public class Bank  implements Serializable {
    protected static int globalTimeUnit = 1;
    private int syncGlobalTimeUnit = 1;
    private ArrayList<Loan> loans = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private Set<String> category=new HashSet<>();
    private Admin bankAdmin;

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

    public Admin getBankAdmin() {
        return bankAdmin;
    }

    public Set<String> getCategory() {
        return category;
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void addLoan(Customer customer, double loanSum, int totalTimeUnit, String reason, int interestPercent, int paymentFrequency) throws UndefinedReasonException, NegativeLoanSumException, NegativeTotalTimeUnitException, NegativeInterestPercentException, NegativePaymentFrequencyException, OverPaymentFrequencyException, UndividedPaymentFrequencyException {
        Loan newLoan = customer.createLoan(customer.getName(), loanSum, totalTimeUnit, reason, interestPercent, paymentFrequency);
        loans.add(newLoan);
    }

    public void addCustumer(String name, double balance, String isAdmin) throws NegativeBalanceException {
        Boolean isAdminInt = Boolean.parseBoolean(isAdmin);
        if(!isAdminInt) {
            Customer newCustomer = new Customer(name, balance, isAdminInt);
            customers.add(newCustomer);
        }
        else{
            Admin newAdmin = new Admin(name,isAdminInt);
            bankAdmin = newAdmin;
        }
    }

    public void checkRiskStatus(){
        for(Loan loan: getLoans()){
            if(loan.getStatus() != Status.Finished)
                loan.checkRiskStatus(getCustomers());
        }
    }

    public ArrayList<LoanDto> getLoansDto() {
        ArrayList<LoanDto> loansDto = new ArrayList<>();
        for (Loan loan : loans) {
            loansDto.add(loan.getLoanDto());
        }
        return loansDto;
    }

    public synchronized ArrayList<CustomerDto> getCustomersDto() {
        ArrayList<CustomerDto> customersDto = new ArrayList<>();
        for (Customer customer : customers) {
            customersDto.add(customer.getCustomerDto());
        }
        return customersDto;
    }

    public void loadXmlData(String username,AbsDescriptor descriptor) throws  UndefinedReasonException, NegativeLoanSumException, NegativeTotalTimeUnitException, NegativeInterestPercentException, NegativePaymentFrequencyException, OverPaymentFrequencyException, UndividedPaymentFrequencyException, NotInCategoryException  {

        Customer loanCustomer = null;

        for(Customer customer: customers){
            if(customer.getName().equals(username)){
                loanCustomer = customer;
                break;
            }
        }
        Set<String> listOfCategory=new HashSet<>();
        for(String s:descriptor.getAbsCategories().getAbsCategory()){
            listOfCategory.add(s);
           // category.add(s);
        }
        category.addAll(listOfCategory);

       AbsLoans absLoans = descriptor.getAbsLoans();
        for(AbsLoan absLoan: absLoans.getAbsLoan()) {

            if(listOfCategory.contains(absLoan.getAbsCategory())) {
                Loan newLoan = new Loan(absLoan.getId(), loanCustomer, absLoan.getAbsCapital(), absLoan.getAbsTotalYazTime(), absLoan.getAbsCategory(),
                        (int) absLoan.getAbsIntristPerPayment(), absLoan.getAbsPaysEveryYaz());
                Boolean exist = false;
                for(LoanDto loan: this.getLoansDto()){
                    if(loan.getLoanName().equals(newLoan.getLoanName())) {
                        exist = true;
                        throw new UndefinedReasonException("");
                    }
                }
                if(!exist){
                    this.loans.add(newLoan);
                    loanCustomer.addOutgoingLoan(newLoan);
                }
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

    public void updateAllDtos() {
        for (Loan loan : loans) {

                loan.updateLoanDto();
                   }
        for (Customer customer : customers) {
            customer.updateCustomerDto();
        }
    }

    public ArrayList<String> getCustomersNames() {
        ArrayList<String> names = null;
        for(Customer customer: customers){
            names.add(customer.getName());
        }
        return names;
    }

    public Boolean adminLogged() {
        Admin admin = getBankAdmin();
        if(admin != null)
            return true;
        else
            return false;
    }
}
