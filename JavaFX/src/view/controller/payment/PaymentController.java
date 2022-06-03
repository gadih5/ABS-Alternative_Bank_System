package view.controller.payment;

import bank.Customer;
import bank.CustomerDto;
import bank.Loan;
import bank.PreTransaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.controller.customer.CustomerController;
import view.controller.payDialog.PayDialogController;
import view.controller.withdrawDialog.WithdrawDialogController;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PaymentController {
    @FXML
    private TableView<Loan> borrowerLoansTable;
    @FXML
    private Button payButton;
    @FXML
    private VBox notificationsVBox;
    @FXML
    private CustomerController customerController;
    @FXML
    private ScrollPane payDialogComponent;
    @FXML
    private PayDialogController payDialogComponentController;
    private CustomerDto selectedCustomer;

    public void setMainController(CustomerController customerController) {
        this.customerController = customerController;
    }

    public void showPaymentInfo(CustomerDto selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
        makeBorrowerLoansTable(selectedCustomer.getOutgoingLoans());
        showNotifications();

    }

    public void showNotifications() {
        notificationsVBox.getChildren().clear();
        for(PreTransaction preTransaction: selectedCustomer.getPreTransactions()){
            notificationsVBox.getChildren().add(new Label(preTransaction.toString()));
        }
        if(!notificationsVBox.getChildren().isEmpty())
            payButton.setDisable(false);

    }



    @FXML
    private void makeBorrowerLoansTable(Collection<Loan> outgoingLoans) {
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
        Set<Loan> setOfLoans = new HashSet<>();
        for(Loan loan: outgoingLoans){
            if(loan.isActive()){
                setOfLoans.add(loan);
            }
        }
        ObservableList<Loan> listOfLoans = FXCollections.observableArrayList(setOfLoans);
        borrowerLoansTable.setItems(listOfLoans);
    }
    @FXML
    void openPayDialog(ActionEvent event) {
        Loan chosenLoan = borrowerLoansTable.getSelectionModel().getSelectedItem();
        if(chosenLoan != null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("../payDialog/payDialog.fxml");
            fxmlLoader.setLocation(url);
            try {
                payDialogComponent = fxmlLoader.load(url.openStream());
                payDialogComponentController = fxmlLoader.getController();
                payDialogComponentController.setMainController(this);
                Loan payLoan = borrowerLoansTable.getSelectionModel().getSelectedItem();
                Set<PreTransaction> preTransactionSet = new HashSet<>();
                for (PreTransaction preTransaction : selectedCustomer.getPreTransactions()) {
                    if (preTransaction.getLoan().equals(payLoan) && !preTransaction.isPaid())
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
}

