package controller.payment;

import bank.*;
import bank.exception.NegativeBalanceException;
import controller.customer.CustomerController;
import controller.payDialog.PayDialogController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PaymentController {
    @FXML
    private TableView<LoanDto> borrowerLoansTable;
    @FXML
    private Button payButton;
    @FXML
    private VBox notificationsVBox;
    @FXML
    private CustomerController customerController;
    @FXML
    private ScrollPane payDialogComponent;
    @FXML
    private Button payEntireButton;
    @FXML
    private PayDialogController payDialogComponentController;
    @NotNull
    private CustomerDto selectedCustomer;

    public void setMainController(CustomerController customerController) {
        this.customerController = customerController;
    }

    public synchronized void showPaymentInfo(CustomerDto selectedCustomer) {
        try {
            this.selectedCustomer = selectedCustomer;
            //borrowerLoansTable.getItems().clear();
            //borrowerLoansTable.getColumns().clear();
            Platform.runLater(()-> {
                makeBorrowerLoansTable(customerController.getOutgoingLoans(this.selectedCustomer.getName()));
                showNotifications();
            });
        }catch(Exception e){

        }

    }

    public void showNotifications() {
        notificationsVBox.getChildren().clear();
        try {
            Customer customer = getSpecificCustomer(this.selectedCustomer.getName());
            for (PreTransaction preTransaction : customer.getPreTransactions()) {
                Platform.runLater(() -> {
                    notificationsVBox.getChildren().add(new Label(preTransaction.toString()));
                });
            }
            if (!notificationsVBox.getChildren().isEmpty())
                payButton.setDisable(false);
        }
        catch(Exception e){}
        }

    @FXML
    private void makeBorrowerLoansTable(Collection<LoanDto> outgoingLoans) {
        TableColumn loanNameColumn = new TableColumn("Loan Name");
        loanNameColumn.setCellValueFactory(new PropertyValueFactory<>("loanName"));

        TableColumn reasonColumn = new TableColumn("Category");
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));

        TableColumn loanSumColumn = new TableColumn("Fund Sum");
        loanSumColumn.setCellValueFactory(new PropertyValueFactory<>("loanSum"));

        TableColumn paymentFrequencyColumn = new TableColumn("Payment Frequency");
        paymentFrequencyColumn.setCellValueFactory(new PropertyValueFactory<>("paymentFrequency"));

        TableColumn interestPercentColumn = new TableColumn("Interest");
        interestPercentColumn.setCellValueFactory(new PropertyValueFactory<>("interestPercent"));

        TableColumn statusColumn = new TableColumn("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn amountToCompleteColumn = new TableColumn("Remain Invest Sum");
        amountToCompleteColumn.setCellValueFactory(new PropertyValueFactory<>("amountToComplete"));

        TableColumn nextPaymentColumn = new TableColumn("Next Payment YAZ");
        nextPaymentColumn.setCellValueFactory(new PropertyValueFactory<>("nextPayment"));

        TableColumn nextPaymentValueColumn = new TableColumn("Next Payment Sum");
        nextPaymentValueColumn.setCellValueFactory(new PropertyValueFactory<>("nextPaymentValue"));

        TableColumn numOfDebtsColumn = new TableColumn("Number of Debts");
        numOfDebtsColumn.setCellValueFactory(new PropertyValueFactory<>("numOfDebts"));

        TableColumn sumOfDebtsColumn = new TableColumn("Sum of Debts");
        sumOfDebtsColumn.setCellValueFactory(new PropertyValueFactory<>("sumOfDebts"));

        TableColumn startTimeUnitColumn = new TableColumn("Start YAZ");
        startTimeUnitColumn.setCellValueFactory(new PropertyValueFactory<>("startTimeUnit"));

        TableColumn finishTimeUnitColumn = new TableColumn("Finish YAZ");
        finishTimeUnitColumn.setCellValueFactory(new PropertyValueFactory<>("finishTimeUnit"));

        borrowerLoansTable.getColumns().addAll(loanNameColumn, reasonColumn, loanSumColumn, paymentFrequencyColumn, interestPercentColumn, statusColumn, amountToCompleteColumn, nextPaymentColumn, nextPaymentValueColumn, numOfDebtsColumn, sumOfDebtsColumn, startTimeUnitColumn, finishTimeUnitColumn);
        borrowerLoansTable.setItems(null);
        Set<LoanDto> setOfLoans = new HashSet<>();
        for(LoanDto loan: outgoingLoans){
            if(loan.isActive() && loan.getStatus() != Status.Finished){
                setOfLoans.add(loan);
            }
        }
        ObservableList<LoanDto> listOfLoans = FXCollections.observableArrayList(setOfLoans);
        borrowerLoansTable.setItems(listOfLoans);
    }

    @FXML
    void openPayDialog(ActionEvent event) {
        LoanDto chosenLoan = borrowerLoansTable.getSelectionModel().getSelectedItem();
        if(chosenLoan != null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("/controller/payDialog/payDialog.fxml");
            fxmlLoader.setLocation(url);
            try {
                payDialogComponent = fxmlLoader.load(url.openStream());
                payDialogComponentController = fxmlLoader.getController();
                payDialogComponentController.setMainController(this);
                LoanDto payLoan = borrowerLoansTable.getSelectionModel().getSelectedItem();
                Set<PreTransaction> preTransactionSet = new HashSet<>();
                Customer customer = getSpecificCustomer(this.selectedCustomer.getName());
                for (PreTransaction preTransaction : customer.getPreTransactions()){
                    if (preTransaction.getLoan().getLoanName().equals(payLoan.getLoanName()) && !preTransaction.isPaid())
                        preTransactionSet.add(preTransaction);
                }
                payDialogComponentController.loadLoanPayments(preTransactionSet, selectedCustomer);
                Scene dialogScene = new Scene(payDialogComponent, 572, 452);
                Stage stage = new Stage();
                stage.setScene(dialogScene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Warning: No Loan Chosen");
            alert.setContentText("Please choose a loan, click OK and try again:");
            alert.showAndWait();
        }
    }


    public Customer getSpecificCustomer(String name) {
       return customerController.getSpecificCustomer(name);
    }

    public void updateAllDtos() {
        customerController.updateBankDtos();
    }

    public void showInfoTable(CustomerDto selectedCustomer) {
        customerController.showInfoTable(selectedCustomer);
    }

    public CustomerDto getSpecificCustomerDto(String name) {
        return customerController.getSpecificCustomerDto(name);
    }

    public void checkLoanStatus(Loan loanToCheck) {
        customerController.checkLoanStatus(loanToCheck);
    }

    public void payEntireLoan(ActionEvent actionEvent) {

        LoanDto selectedLoan = borrowerLoansTable.getSelectionModel().getSelectedItem();
        if(selectedLoan == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Warning: No Loan Chosen");
            alert.setContentText("Please choose a loan, click OK and try again:");
            alert.showAndWait();
        }
        else {
            Loan theLoan = getSpecificLoan(selectedLoan.getLoanName());
            int leftPayments = selectedLoan.getRemainTimeUnit() / selectedLoan.getPaymentFrequency();
            for (Fraction fraction : customerController.getFractions(selectedLoan.getLoanName())) {
                double singlePayment = (fraction.getAmount() + (fraction.getAmount() * (selectedLoan.getInterestPercent()) / 100.0)) / (selectedLoan.getTotalTimeUnit() / selectedLoan.getPaymentFrequency());
                double totalPaymentAmount = singlePayment * leftPayments;
                Customer customer = customerController.getSpecificCustomer(this.selectedCustomer.getName());
                for (PreTransaction preTransaction : customer.getPreTransactions()) {
                    if (!preTransaction.isPaid() && preTransaction.getLoan() == selectedLoan) {
                        totalPaymentAmount += preTransaction.getSum();
                    }
                }
                try {
                    customerController.addTranctions(customer.getName(),fraction.getCustomer().getName(),
                            String.valueOf(totalPaymentAmount));
                    customerController.makeAllPreTransactionsPaid(customer.getName(),selectedLoan.getLoanName());
                    customerController.clearAllDebts(selectedLoan);
                    customerController.setStatusFinished(selectedLoan);
                    customerController.showInfoTable(customer.getCustomerDto());
                    showPaymentInfo(customer.getCustomerDto());

                } catch (NegativeBalanceException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setHeaderText("Warning: Negative Balance");
                    alert.setContentText(customer.getName() + " not have enough money in account balance");
                    alert.showAndWait();
                }

            }
        }
    }

    private Loan getSpecificLoan(String loanName) {
        return customerController.getSpecificLoan(loanName);
    }

    public synchronized void tableClicked(MouseEvent mouseEvent) {
        if(!borrowerLoansTable.getItems().isEmpty()){
            LoanDto selectedLoan = borrowerLoansTable.getSelectionModel().getSelectedItem();
            if(selectedLoan != null){
                payEntireButton.setDisable(false);
                payButton.setDisable(false);
            }
        }
    }

    public void setCustomer(CustomerDto selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public Loan getSpecficLoan(String loanName) {
        return customerController.getSpecificLoan(loanName);
    }

    public boolean makeTransaction(String id, String name) throws NegativeBalanceException {
       return customerController.makeTransaction(id,name);
    }
}

