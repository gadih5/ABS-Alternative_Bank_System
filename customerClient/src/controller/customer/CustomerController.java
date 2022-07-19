package controller.customer;

import bank.Customer;
import bank.CustomerDto;
import bank.Loan;
import bank.LoanDto;
import controller.app.CustomerAppController;
import controller.information.InformationController;
import controller.payment.PaymentController;
import controller.scramble.ScrambleController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import org.jetbrains.annotations.Nullable;
import utils.Listener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

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

   /* public synchronized void getCustomersDto(Listener listner){
        new Thread(() -> {
            ArrayList<CustomerDto> customersDto;
            customersDto = customerAppController.getCustomersDto();

            System.out.println("TEST: " + customersDto);

            listner.OnCall(customersDto);
        }).start();x
    }*/

    public synchronized void loadCustomerDetails(String userName, boolean fromScramble)  {
                customerAppController.updateBankDtos();
                ArrayList<CustomerDto> customersDto = customerAppController.getCustomersDto() ;
                for(CustomerDto customerDto : customersDto){
                    if(customerDto.getName().equals(userName)) {
                        selectedCustomer = customerDto;
                        informationComponentController.setCustomer(selectedCustomer);
                    }
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

    public ArrayList<Customer> getCustomers() {
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
        Platform.runLater(() -> {
            informationComponentController.showInfoTable(customerDto);
        });
    }

    public void selfTransaction(String name, int amount) {
        customerAppController.selfTransaction(name,amount);
    }


    public void clearAllDebts(LoanDto selectedLoan) {
        customerAppController.clearAllDebts(selectedLoan);
    }

    public void setStatusFinished(LoanDto selectedLoan) {
        customerAppController.setStatusFinished(selectedLoan);
    }
}
