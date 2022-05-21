package view.controller.information;

import bank.Customer;
import bank.CustomerDto;
import bank.Transaction;
import bank.exception.NegativeBalanceException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import view.controller.chargeDialog.ChargeDialogController;
import view.controller.customer.CustomerController;
import view.controller.withdrawDialog.WithdrawDialogController;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;

public class InformationController {
        @FXML
        private Label balanceLabel;
        @FXML
        private TableView<Transaction> accountTransTable;
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
        accountTransTable.getColumns().clear();
        makeTransactionsTable(selectedCustomer.getTransactions());
        balanceLabel.setText("Balance: " + selectedCustomer.getBalance());
        //TODO Show 'borrower loans table' & 'loaner loans table'


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
        URL url = getClass().getResource("../chargeDialog/chargeDialog.fxml");
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
        URL url = getClass().getResource("../withdrawDialog/withdrawDialog.fxml");
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


