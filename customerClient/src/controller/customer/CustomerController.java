package controller.customer;

import bank.*;
import bank.exception.NegativeBalanceException;
import controller.app.CustomerAppController;
import controller.information.InformationController;
import controller.payment.PaymentController;
import controller.scramble.ScrambleController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.*;

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
    @FXML
    private TabPane customerComponent;
    @FXML
    private Tab infoTab;
    @FXML
    private AnchorPane infoAnchor;
    @FXML
    private Tab scrambleTab;
    @FXML
    private AnchorPane scrambleAnchor;
    @FXML
    private Tab paymentTab;
    @FXML
    private AnchorPane paymentAnchor;

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

    public synchronized void loadCustomerDetails(String userName, boolean fromScramble)  {
                customerAppController.updateBankDtos();
                ArrayList<CustomerDto> customersDto = customerAppController.getCustomersDto() ;
                for(CustomerDto customerDto : customersDto){
                    if(customerDto.getName().equals(userName)) {
                        selectedCustomer = customerDto;
                        informationComponentController.setCustomer(selectedCustomer);
                        scrambleComponentController.setCustomer(selectedCustomer);
                        paymentComponentController.setCustomer(selectedCustomer);
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

    public Collection<LoanDto> getLoansDto(int categoriesChose, Set<String> chosenCategories, int minInterestPercent, int minTotalYaz, int maxOpenLoans, int maxOwnershipPercent, CustomerDto selectedCustomer) {
        return customerAppController.getLoansDtoForScramble(categoriesChose, chosenCategories,minInterestPercent,minTotalYaz,maxOpenLoans,maxOwnershipPercent, selectedCustomer);
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

    public void refreshInfo(CustomerDto customerDto) {
        Platform.runLater(() -> {
            informationComponentController.showInfoTable(customerDto);
            scrambleComponentController.loadScrambleInfo(customerDto);
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

    public CustomerDto getTheCustomer() {
        return selectedCustomer;
    }

    public void loadTabs() {
        URL url = getClass().getResource("/controller/information/information.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        try {
            informationComponent = fxmlLoader.load(url.openStream());
            informationComponentController = fxmlLoader.getController();
            setInformationComponentController(informationComponentController);
            infoAnchor.getChildren().add(informationComponent);
            AnchorPane.setBottomAnchor(informationComponent, 0.0);
            AnchorPane.setLeftAnchor(informationComponent, 0.0);
            AnchorPane.setRightAnchor(informationComponent, 0.0);
            AnchorPane.setTopAnchor(informationComponent, 0.0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        url = getClass().getResource("/controller/scramble/scramble.fxml");
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        try {
            scrambleComponent = fxmlLoader.load(url.openStream());
            scrambleComponentController = fxmlLoader.getController();
            setScrambleComponentController(scrambleComponentController);
            scrambleAnchor.getChildren().add(scrambleComponent);
            AnchorPane.setBottomAnchor(scrambleComponent, 0.0);
            AnchorPane.setLeftAnchor(scrambleComponent, 0.0);
            AnchorPane.setRightAnchor(scrambleComponent, 0.0);
            AnchorPane.setTopAnchor(scrambleComponent, 0.0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        url = getClass().getResource("/controller/payment/payment.fxml");
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        try {
            paymentComponent = fxmlLoader.load(url.openStream());
            paymentComponentController = fxmlLoader.getController();
            setPaymentComponentController(paymentComponentController);
            paymentAnchor.getChildren().add(paymentComponent);
            AnchorPane.setBottomAnchor(paymentComponent, 0.0);
            AnchorPane.setLeftAnchor(paymentComponent, 0.0);
            AnchorPane.setRightAnchor(paymentComponent, 0.0);
            AnchorPane.setTopAnchor(paymentComponent, 0.0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Transaction> getTransactions(String name) {
        return customerAppController.getTransactions(name);
    }

    public double getNewBalance(String name) {
        return customerAppController.getNewBalance(name);
    }

    public void addLoaner(String loanName, String name, double sumToinvest) {
        customerAppController.addLoaner(loanName,name,sumToinvest);
    }

    public ArrayList<LoanDto> getOutgoingLoans(String name) {
        ArrayList<LoanDto> allLoans = customerAppController.getLoansDto();
        ArrayList<LoanDto> outgoingLoans = new ArrayList<>();
        for(LoanDto loanDto: allLoans){
            if(loanDto.getBorrowerName().equals(name))
                outgoingLoans.add(loanDto);
        }
        return outgoingLoans;
    }

    public Set<LoanDto> getIngoingLoans(String name) {
        Set<LoanDto> ingoingLoans = new HashSet<>();
        try {
            ArrayList<LoanDto> allLoans = customerAppController.getLoansDto();
            if (!allLoans.isEmpty()) {
                for (LoanDto loanDto : allLoans) {
                    if (loanDto != null) {
                        for (String string : loanDto.getLoaners()) {
                            if (string.equals(name))
                                ingoingLoans.add(loanDto);
                        }
                    }
                }
            }
        }catch (Exception e) {
        }
        return ingoingLoans;
    }

    public void setSelectedCustomer(CustomerDto selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public void showPaymentInfo(CustomerDto selectedCustomer) {
        paymentComponentController.showPaymentInfo(selectedCustomer);
    }

    public boolean makeTransaction(String id, String name) throws NegativeBalanceException {
        return customerAppController.makeTransaction(id,name);
    }

    public void addTransaction(String name, String name1, String valueOf) throws NegativeBalanceException {
        customerAppController.addTransition(name,name1,valueOf);
    }

    public void makeAllPreTransactionsPaid(String name, String selectedLoan) {
        customerAppController.makeAllPreTransactionsPaid(name,selectedLoan);
    }

    public  Collection<Fraction> getFractions (String loanName) {
       return customerAppController.getFractions(loanName);
    }
}