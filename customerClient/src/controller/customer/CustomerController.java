package controller.customer;

import bank.Customer;
import bank.CustomerDto;
import bank.Loan;
import bank.LoanDto;
import controller.app.CustomerAppController;
import controller.information.InformationController;
import controller.payment.PaymentController;
import controller.scramble.ScrambleController;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CustomerController {
    @FXML
    private CustomerAppController customerAppController;
    @FXML
    private SplitPane paymentComponent;
    @FXML
    private PaymentController paymentComponentController;
    @FXML
    private SplitPane informationComponent;
    @FXML
    private InformationController informationComponentController;
    @FXML
    private SplitPane scrambleComponent;
    @FXML
    private ScrambleController scrambleComponentController;

    private CustomerDto selectedCustomer = null;

    public void setMainController(CustomerAppController customerAppController) {
        this.customerAppController = customerAppController;
    }

    @FXML
    public void initialize() {
        if (informationComponentController != null && paymentComponentController != null && scrambleComponentController != null) {
            informationComponentController.setMainController(this);
            paymentComponentController.setMainController(this);
            scrambleComponentController.setMainController(this);
        }
    }

    public void setPaymentComponentController(PaymentController paymentComponentController) {
        this.paymentComponentController = paymentComponentController;
        paymentComponentController.setMainController(this);
    }

    public void setInformationComponentController(InformationController informationComponentController) {
        this.informationComponentController = informationComponentController;
        informationComponentController.setMainController(this);
    }

    public void setScrambleComponentController(ScrambleController scrambleComponentController) {
        this.scrambleComponentController = scrambleComponentController;
        scrambleComponentController.setMainController(this);
    }

    public CustomerDto getSelectedCustomer() {
        return selectedCustomer;
    }


    public void loadCustomerDetails(String userName, boolean fromScramble)  {
        customerAppController.updateBankDtos();
        ArrayList<CustomerDto> customersDto = customerAppController.getCustomersDto();
        System.out.println("THE RESULT: " + customersDto);
        for(CustomerDto customerDto : customersDto){
            if(customerDto.getName().equals(userName))
                selectedCustomer = customerDto;
        }
        if(selectedCustomer == null)
            return;
        else { //Bank's customer
            customerAppController.setName(selectedCustomer.getName());
            informationComponentController.showInfoTable(selectedCustomer);
            if(!fromScramble)
                scrambleComponentController.loadScrambleInfo(selectedCustomer);
            paymentComponentController.showPaymentInfo(selectedCustomer);
        }
    }

    public Collection<Customer> getCustomers() {
        return customerAppController.getCustomers();
    }

    public Collection<CustomerDto> getCustomersDto() {
        return customerAppController.getCustomersDto();
    }

    public Set<String> getCategories() {
        return customerAppController.getCategories();
    }

    public int calcMaxInterestPercent(CustomerDto selectedCustomer) {
        return customerAppController.calcMaxInterestPercent(selectedCustomer);
    }

    public int calcMaxTotalYaz(CustomerDto selectedCustomer) {
        return customerAppController.calcMaxTotalYaz(selectedCustomer);
    }

    public int getNumOfLoans() {
        return customerAppController.getNumOfLoans();
    }

    public Collection<LoanDto> getLoansDto(int categoriesChosed, Set<String> chosenCategories, int minInterestPercent, int minTotalYaz, int maxOpenLoans, int maxOwnershipPercent, CustomerDto selectedCustomer) {
        return customerAppController.getLoansDtoForScramble(categoriesChosed, chosenCategories,minInterestPercent,minTotalYaz,maxOpenLoans,maxOwnershipPercent, selectedCustomer);
    }

    public Loan getSpecificLoan(String loanName) {
        return customerAppController.getSpecificLoan(loanName);
    }

    public Customer getSpecificCustomer(String name) {
        return customerAppController.getSpecificCustomer(name);
    }

    public void showInfoTable(CustomerDto selectedCustomer) {
        informationComponentController.showInfoTable(selectedCustomer);
    }

    public void updateBankDtos() {
        customerAppController.updateBankDtos();
    }

    public CustomerDto getSpecificCustomerDto(String name) {
        return customerAppController.getSpecificCustomerDto(name);
    }

    public void checkLoanStatus(Loan loanToCheck) {
        customerAppController.checkLoanStatus(loanToCheck);
    }

    public void refershInfo(CustomerDto customerDto) {
        informationComponentController.showInfoTable(customerDto);
    }
}
