package controller.information;

import bank.*;
import controller.chargeDialog.ChargeDialogController;
import controller.customer.CustomerController;
import controller.withdrawDialog.WithdrawDialogController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class InformationController {
        @FXML
        private Label balanceLabel;
        @FXML
        private TableView<Transaction> accountTransTable;
        @FXML
        private TableView<LoanDto> borrowerLoansTable;
        @FXML
        private TableView<LoanDto> loanerLoansTable;
        @FXML
        private CustomerController customerController;
        @FXML
        private ScrollPane chargeDialogComponent;
        @FXML
        private ChargeDialogController chargeDialogComponentController;
        @FXML
        private ScrollPane withdrawDialogComponent;
        @FXML
        private WithdrawDialogController withdrawDialogComponentController;

        private CustomerDto selectedCustomer;

    public void setMainController(CustomerController customerController) {
        this.customerController = customerController;
    }

    @FXML
    public void showInfoTable(CustomerDto selectedCustomer) {
        try {

            accountTransTable.getItems().clear();
            accountTransTable.getColumns().clear();
            ArrayList<Transaction> transactions = getTransactions(this.selectedCustomer.getName());
            makeTransactionsTable(transactions);

            double b = getNewBalance(this.selectedCustomer.getName());
            balanceLabel.setText("Balance: " + b);

            borrowerLoansTable.getItems().clear();
            borrowerLoansTable.getColumns().clear();
            ArrayList<LoanDto> outLoans = getOutgoingLoans(this.selectedCustomer.getName());
            makeBorrowerLoansTable(outLoans);
            borrowerLoansTable.getSortOrder().add(borrowerLoansTable.getColumns().get(0));

            Set<LoanDto> inLoans = getIngoingLoans(this.selectedCustomer.getName());
            loanerLoansTable.getItems().clear();
            loanerLoansTable.getColumns().clear();
            makeLoanerLoansTable(inLoans);
            loanerLoansTable.getSortOrder().add(loanerLoansTable.getColumns().get(0));
        }catch (Exception e){

        }

    }

    private Set<LoanDto> getIngoingLoans(String name) {
       return customerController.getIngoingLoans(name);
    }

    private ArrayList<LoanDto> getOutgoingLoans(String name) {
        return customerController.getOutgoingLoans(name);
    }

    private double getNewBalance(String name) {
        return customerController.getNewBalance(name);
    }

    private ArrayList<Transaction> getTransactions(String name) {
        return customerController.getTransactions(name);
    }

    @FXML
    private void makeLoanerLoansTable(Collection<LoanDto> ingoingLoans) {
        TableColumn loanNameColumn = new TableColumn("Loan Name");
        loanNameColumn.setCellValueFactory(new PropertyValueFactory<>("loanName"));


        TableColumn reasonColumn = new TableColumn("Category");
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));

        TableColumn loanSumColumn = new TableColumn("Fund Sum");
        loanSumColumn.setCellValueFactory(new PropertyValueFactory<>("loanSum"));

        TableColumn paymentFrequencyColumn = new TableColumn("Payment Frequency");
        paymentFrequencyColumn.setCellValueFactory(new PropertyValueFactory<>("paymentFrequency"));

        TableColumn interestPercentColumn = new TableColumn("Interest");
        interestPercentColumn.setCellValueFactory(new PropertyValueFactory<>("interestPrecent"));

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

        loanerLoansTable.getColumns().addAll(loanNameColumn, reasonColumn, loanSumColumn, paymentFrequencyColumn, interestPercentColumn, statusColumn, amountToCompleteColumn, nextPaymentColumn, nextPaymentValueColumn, numOfDebtsColumn, sumOfDebtsColumn, startTimeUnitColumn, finishTimeUnitColumn);
        loanerLoansTable.setItems(null);
        Set<LoanDto> setOfLoans = new HashSet<>();
        setOfLoans.addAll(ingoingLoans);
        ObservableList<LoanDto> listOfLoans = FXCollections.observableArrayList(setOfLoans);
      //  SortedList<LoanDto> sortedList = new SortedList<>(listOfLoans);
        loanerLoansTable.setItems(listOfLoans);
    }

    @FXML
    private synchronized void makeBorrowerLoansTable(Collection<LoanDto> outgoingLoans) {
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
        setOfLoans.addAll(outgoingLoans);
        ObservableList<LoanDto> listOfLoans = FXCollections.observableArrayList(setOfLoans);
       // SortedList<LoanDto> sortedList = new SortedList<>(listOfLoans);
        borrowerLoansTable.setItems(listOfLoans);
    }

    @FXML
    void makeTransactionsTable(Collection<Transaction> customerTrans) {
        TableColumn yazColumn = new TableColumn("YAZ");
        yazColumn.setCellValueFactory(new PropertyValueFactory<>("timeUnit"));

        TableColumn amountColumn = new TableColumn("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn actionColumn = new TableColumn("Withdraw/Deposit");
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("sign"));

        TableColumn previousBalanceColumn = new TableColumn("Previous Balance");
        previousBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("previousBalance"));

        TableColumn afterBalanceColumn = new TableColumn("After Balance");
        afterBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("afterBalance"));

        accountTransTable.getColumns().addAll(yazColumn, amountColumn, actionColumn, previousBalanceColumn, afterBalanceColumn);
        accountTransTable.setItems(null);
        ObservableList<Transaction> listOfTrans = FXCollections.observableArrayList(customerTrans);
        accountTransTable.setItems(listOfTrans);
    }

    public void chargeMoney(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/controller/chargeDialog/chargeDialog.fxml");
        fxmlLoader.setLocation(url);
        try {
            chargeDialogComponent = fxmlLoader.load(url.openStream());
            chargeDialogComponentController =  fxmlLoader.getController();
            chargeDialogComponentController.setMainController(this);
            Scene dialogScene = new Scene(chargeDialogComponent, 405, 205);
            Stage stage = new Stage();
            stage.setScene(dialogScene);
            stage.show();
            chargeDialogComponentController.focusTextField();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chargeAmount(String text) {
        try {
            int amount = Integer.parseInt(text);
            if (amount <= 0) {
                chargeMoney(new ActionEvent());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error: Invalid Number");
                alert.setContentText("Please enter a positive integer, click OK and try again:");
                alert.showAndWait();
            }
            else {
                this.selectedCustomer = customerController.getTheCustomer();
                customerController.setSelectedCustomer(this.selectedCustomer);

                if (selectedCustomer != null) {
                    customerController.selfTransaction(selectedCustomer.getName(), amount);
                    showInfoTable(selectedCustomer);
                }

            }
        }catch(NumberFormatException e){
                chargeMoney(new ActionEvent());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error: Invalid Number");
                alert.setContentText("Please enter a positive integer, click OK and try again:");
                alert.showAndWait();
            }
    }

    public void withdrawMoney(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/controller/withdrawDialog/withdrawDialog.fxml");
        fxmlLoader.setLocation(url);
        try {
            withdrawDialogComponent = fxmlLoader.load(url.openStream());
            withdrawDialogComponentController =  fxmlLoader.getController();
            withdrawDialogComponentController.setMainController(this);
            Scene dialogScene = new Scene(withdrawDialogComponent, 405, 205);
            Stage stage = new Stage();
            stage.setScene(dialogScene);
            stage.show();
            withdrawDialogComponentController.focusTextField();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void withdrawAmount(String text) {
        try {
            int amount = Integer.parseInt(text);

            if(amount == 0){
                withdrawMoney(new ActionEvent());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error: Invalid Number");
                alert.setContentText("Please enter a positive/negative integer, click OK and try again:");
                alert.showAndWait();
            }
            this.selectedCustomer = customerController.getTheCustomer();
            if (selectedCustomer != null) {
                 if(Math.abs(amount) > getNewBalance(selectedCustomer.getName())){
                    withdrawMoney(new ActionEvent());
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setHeaderText("Warning: Negative Balance");
                    alert.setContentText(selectedCustomer.getName() + " not have enough money in account balance");
                    alert.showAndWait();
                }
                else if(amount < 0)
                    customerController.selfTransaction(selectedCustomer.getName(), amount);
                else if(amount > 0)
                    customerController.selfTransaction(selectedCustomer.getName(), -amount);

                showInfoTable(selectedCustomer);
            }

        }
        catch (NumberFormatException e){
            withdrawMoney(new ActionEvent());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error: Invalid Number");
            alert.setContentText("Please enter a positive/negative integer, click OK and try again:");
            alert.showAndWait();
        }
    }

    public void setCustomer(CustomerDto selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }
}


