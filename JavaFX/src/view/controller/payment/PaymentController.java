package view.controller.payment;


import bank.CustomerDto;
import bank.Loan;
import bank.PreTransaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import view.controller.customer.CustomerController;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PaymentController {
    @FXML
    private TableView<Loan> borrowerLoansTable;
    @FXML
    private VBox notificationsVBox;
    private CustomerController customerController;
    private CustomerDto selectedCustomer;

    public void setMainController(CustomerController customerController) {
        this.customerController = customerController;
    }

    public void showPaymentInfo(CustomerDto selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
        makeBorrowerLoansTable(selectedCustomer.getOutgoingLoans());
        showNotifications();

    }

    private void showNotifications() {
        notificationsVBox.getChildren().clear();
        for(PreTransaction preTransaction: selectedCustomer.getPreTransactions()){
            notificationsVBox.getChildren().add(new Label(preTransaction.toString()));
        }
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
}

