package view.controller.payDialog;

import bank.Customer;
import bank.CustomerDto;
import bank.Loan;
import bank.PreTransaction;
import bank.exception.NegativeBalanceException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.controller.payment.PaymentController;

import java.util.HashSet;
import java.util.Set;

public class PayDialogController {
    @FXML
    private Button PayButton;
    @FXML
    private TableView<PreTransaction> paymentsTableView;
    @FXML
    private Button cancelBtn;

    private PaymentController paymentController;
    private CustomerDto selectedCustomer;

    public void setMainController(PaymentController paymentController) {
        this.paymentController = paymentController;
    }



    public void payPayment(ActionEvent actionEvent) {
        ObservableList<PreTransaction> listOfPreTransactions = paymentsTableView.getSelectionModel().getSelectedItems();
        if(listOfPreTransactions != null){
            Customer fromCustomer = paymentController.getSpecificCustomer((selectedCustomer.getName()));
            for(PreTransaction preTransaction: listOfPreTransactions){
                try {
                    preTransaction.makeTransaction(fromCustomer);
                    paymentController.showNotifications();
                    paymentController.updateAllDtos();
                    selectedCustomer = paymentController.getSpecificCustomerDto(selectedCustomer.getName());
                    paymentController.showInfoTable(selectedCustomer);
                    Stage stage = (Stage) cancelBtn.getScene().getWindow();
                    stage.close();

                } catch (NegativeBalanceException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setHeaderText("Warning: Negative Balance");
                    alert.setContentText(fromCustomer.getName() + " not have enough money in account balance");
                    alert.showAndWait();
                }
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Warning: No Payment Chosen");
            alert.setContentText("Please choose payments, click OK and try again:");
            alert.showAndWait();
        }
    }

    public void cancelClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void loadLoanPayments(Set<PreTransaction> payLoan, CustomerDto selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
        makePaymentsTable(payLoan);

    }

    private void makePaymentsTable(Set<PreTransaction> preTransactionSet) {
        TableColumn toCustomerColumn = new TableColumn("Loaner");
        toCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("toCustomer"));

        TableColumn sumColumn = new TableColumn("Sum to Pay");
        sumColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));

        TableColumn payTimeColumn = new TableColumn("Payment YAZ");
        payTimeColumn.setCellValueFactory(new PropertyValueFactory<>("payTime"));


        paymentsTableView.getSelectionModel().selectionModeProperty().setValue(SelectionMode.MULTIPLE);
        paymentsTableView.getSelectionModel().setSelectionMode (SelectionMode.MULTIPLE);
        paymentsTableView.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
            Node node = evt.getPickResult().getIntersectedNode();

            // go up from the target node until a row is found or it's clear the
            // target node wasn't a node.
            while (node != null && node != paymentsTableView && !(node instanceof TableRow)) {
                node = node.getParent();
            }

            // if is part of a row or the row,
            // handle event instead of using standard handling
            if (node instanceof TableRow) {
                // prevent further handling
                evt.consume();

                TableRow row = (TableRow) node;
                TableView tv = row.getTableView();

                // focus the tableview
                tv.requestFocus();

                if (!row.isEmpty()) {
                    // handle selection for non-empty nodes
                    int index = row.getIndex();
                    if (row.isSelected()) {
                        tv.getSelectionModel().clearSelection(index);
                    } else {
                        tv.getSelectionModel().select(index);
                    }
                }
            }
        });

        paymentsTableView.getColumns().addAll(toCustomerColumn,sumColumn,payTimeColumn);
        paymentsTableView.setItems(null);
        ObservableList<PreTransaction> listOfPayments = FXCollections.observableArrayList(preTransactionSet);
        paymentsTableView.setItems(listOfPayments);

    }
}