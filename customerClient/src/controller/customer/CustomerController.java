package controller.customer;

import bank.Customer;
import bank.CustomerDto;
import bank.Loan;
import bank.LoanDto;
import controller.app.AppController;
import controller.information.InformationController;
import controller.payment.PaymentController;
import controller.scramble.ScrambleController;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;

import java.util.Collection;
import java.util.Set;

public class CustomerController {
    @FXML
    private AppController appController;
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

    public void setMainController(AppController appController) {
        this.appController = appController;
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

    public void loadCustomerDetails(String userName, boolean fromScramble) {
        appController.updateBankDtos();
        Collection<CustomerDto> customersDto = appController.getCustomersDto();
        for(CustomerDto customerDto : customersDto){
            if(customerDto.getName().equals(userName))
                selectedCustomer = customerDto;
        }
        if(selectedCustomer == null)
            return;
        else { //Bank's customer
            informationComponentController.showInfoTable(selectedCustomer);
            if(!fromScramble)
                scrambleComponentController.loadScrambleInfo(selectedCustomer);
            paymentComponentController.showPaymentInfo(selectedCustomer);
        }
    }

    public Collection<Customer> getCustomers() {
        return appController.getCustomers();
    }

    public Collection<CustomerDto> getCustomersDto() {
        return appController.getCustomersDto();
    }

    public Set<String> getCategories() {
        return appController.getCategories();
    }

    public int calcMaxInterestPercent(CustomerDto selectedCustomer) {
        return appController.calcMaxInterestPercent(selectedCustomer);
    }

    public int calcMaxTotalYaz(CustomerDto selectedCustomer) {
        return appController.calcMaxTotalYaz(selectedCustomer);
    }

    public int getNumOfLoans() {
        return appController.getNumOfLoans();
    }

    public Collection<LoanDto> getLoansDto(int categoriesChosed, Set<String> chosenCategories, int minInterestPercent, int minTotalYaz, int maxOpenLoans, int maxOwnershipPercent, CustomerDto selectedCustomer) {
        return appController.getLoansDtoForScramble(categoriesChosed, chosenCategories,minInterestPercent,minTotalYaz,maxOpenLoans,maxOwnershipPercent, selectedCustomer);
    }

    public Loan getSpecificLoan(String loanName) {
        return appController.getSpecificLoan(loanName);
    }

    public Customer getSpecificCustomer(String name) {
        return appController.getSpecificCustomer(name);
    }

    public void showInfoTable(CustomerDto selectedCustomer) {
        informationComponentController.showInfoTable(selectedCustomer);
    }

    public void updateBankDtos() {
        appController.updateBankDtos();
    }

    public CustomerDto getSpecificCustomerDto(String name) {
        return appController.getSpecificCustomerDto(name);
    }

    public void checkLoanStatus(Loan loanToCheck) {
        appController.checkLoanStatus(loanToCheck);
    }

    public void refershInfo(CustomerDto customerDto) {
        informationComponentController.showInfoTable(customerDto);
    }
}
