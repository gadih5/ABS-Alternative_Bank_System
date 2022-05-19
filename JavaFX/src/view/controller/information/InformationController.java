package view.controller.information;


    import bank.CustomerDto;
    import bank.LoanDto;
    import bank.Transaction;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.control.TableColumn;
    import javafx.scene.control.TableView;
    import javafx.scene.control.cell.PropertyValueFactory;
    import view.controller.customer.CustomerController;

    import java.util.Collection;

    import static view.controller.header.HeaderController.myBank;

public class InformationController {


        @FXML
        private Label balanceLabel;

        @FXML
        private Button chargeBtn;

        @FXML
        private Button withdrowBtn;

        @FXML
        private TableView<Transaction> accountTransTable;


        private CustomerController customerController;

    public void setMainController(CustomerController customerController) {
        this.customerController = customerController;
    }
    @FXML
    public void showInfoTable(CustomerDto selectedCustomer) {

        accountTransTable.getColumns().clear();
        makeTransactionsTable(selectedCustomer.getTransactions());
        balanceLabel.setText("asfdasgsdghdfhdgfjrtyeju4yhu4wyghrtju");

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
}


