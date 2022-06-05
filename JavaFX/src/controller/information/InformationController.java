package controller.information;

import bank.*;
import bank.exception.NegativeBalanceException;
import controller.chargeDialog.ChargeDialogController;
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
import controller.customer.CustomerController;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class InformationController {
        @FXML
        private Label balanceLabel;
        @FXML
        private TableView<Transaction> accountTransTable;
        @FXML
        private TableView<Loan> borrowerLoansTable;
        @FXML
        private TableView<Loan> loanerLoansTable;
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

    public void setMainController(CustomerController customerController) {
        this.customerController = customerController;
    }

    @FXML
    public void showInfoTable(CustomerDto selectedCustomer) {
        accountTransTable.getItems().clear();
        accountTransTable.getColumns().clear();
        makeTransactionsTable(selectedCustomer.getTransactions());
        balanceLabel.setText("Balance: " + selectedCustomer.getBalance());

        borrowerLoansTable.getItems().clear();
        borrowerLoansTable.getColumns().clear();
        makeBorrowerLoansTable(selectedCustomer.getOutgoingLoans());

        loanerLoansTable.getItems().clear();
        loanerLoansTable.getColumns().clear();
        makeLoanerLoansTable(selectedCustomer.getIngoingLoans());
    }

    @FXML
    private void makeLoanerLoansTable(Collection<Loan> ingoingLoans) {
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

        loanerLoansTable.getColumns().addAll(loanNameColumn, reasonColumn, loanSumColumn, paymentFrequencyColumn, interestPercentColumn, statusColumn, amountToCompleteColumn, nextPaymentColumn, nextPaymentValueColumn, numOfDebtsColumn, sumOfDebtsColumn, startTimeUnitColumn, finishTimeUnitColumn);
        loanerLoansTable.setItems(null);
        Set<Loan> setOfLoans = new HashSet<>();
        setOfLoans.addAll(ingoingLoans);
        ObservableList<Loan> listOfLoans = FXCollections.observableArrayList(setOfLoans);
        loanerLoansTable.setItems(listOfLoans);
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
        setOfLoans.addAll(outgoingLoans);
        ObservableList<Loan> listOfLoans = FXCollections.observableArrayList(setOfLoans);
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
        CustomerDto theCustomerDto = customerController.getSelectedCustomer();
        Customer theCustomer = null;
        try {
            int amount = Integer.parseInt(text);
            if (amount <= 0) {
                chargeMoney(new ActionEvent());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error: Invalid Number");
                alert.setContentText("Please enter a positive integer, click OK and try again:");
                alert.showAndWait();
            } else {
                for (Customer customer : customerController.getCustomers()) {
                    if (customer.getName().equals(theCustomerDto.getName())) {
                        theCustomer = customer;
                    }
                }
                try {
                    theCustomer.selfTransaction(amount);
                    for (CustomerDto customerDto : customerController.getCustomersDto()) {
                        if (customerDto.getName().equals(theCustomer.getName())) {
                            theCustomerDto = customerDto;
                        }
                    }
                    showInfoTable(theCustomerDto);
                } catch (NegativeBalanceException e) { //Can't get this exception
                    e.printStackTrace();
                }
            }
        }
        catch(NumberFormatException e){
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
        CustomerDto theCustomerDto = customerController.getSelectedCustomer();
        Customer theCustomer = null;
        try {
            int amount = Integer.parseInt(text);

            for(Customer customer: customerController.getCustomers()){
                if (customer.getName().equals(theCustomerDto.getName()))
                    theCustomer = customer;
            }
            try {
                if(amount == 0){
                    withdrawMoney(new ActionEvent());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Error: Invalid Number");
                    alert.setContentText("Please enter a positive/negative integer, click OK and try again:");
                    alert.showAndWait();
                }
                if(amount < 0)
                    theCustomer.selfTransaction(amount);
                else if(amount>0)
                    theCustomer.selfTransaction(-amount);

                for (CustomerDto customerDto : customerController.getCustomersDto()) {
                    if (customerDto.getName().equals(theCustomer.getName()))
                        theCustomerDto = customerDto;
                }
                showInfoTable(theCustomerDto);

            } catch (NegativeBalanceException e) {
                withdrawMoney(new ActionEvent());
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Warning: Negative Balance");
                alert.setContentText(theCustomer.getName() + " not have enough money in account balance");
                alert.showAndWait();
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
}


