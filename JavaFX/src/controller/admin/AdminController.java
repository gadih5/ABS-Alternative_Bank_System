package controller.admin;

import bank.CustomerDto;
import bank.LoanDto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import controller.app.AppController;
import java.util.Collection;

public class AdminController{
    @FXML
    private Button increaseYazBtn;
    @FXML
    private TableView<CustomerDto> customersTable;
    @FXML
    private TableView<LoanDto> loansTable;
    private AppController appController;

    @FXML
    public void makeAdminLoansTable() {
        TableColumn nameColumn = new TableColumn("Loan Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("loanName"));

        TableColumn borrowerNameColumn = new TableColumn("Borrower Name");
        borrowerNameColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerName"));

        TableColumn reasonColumn = new TableColumn("Reason");
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));

        TableColumn loanSumColumn = new TableColumn("Loan Sum");
        loanSumColumn.setCellValueFactory(new PropertyValueFactory<>("loanSum"));

        TableColumn totalLoanTimeColumn = new TableColumn("Total Loan Time");
        totalLoanTimeColumn.setCellValueFactory(new PropertyValueFactory<>("totalTimeUnit"));

        TableColumn interestColumn = new TableColumn("Interest");
        interestColumn.setCellValueFactory(new PropertyValueFactory<>("interestPrecent"));

        TableColumn paymentFrequencyColumn = new TableColumn("Payment Frequency");
        paymentFrequencyColumn.setCellValueFactory(new PropertyValueFactory<>("paymentFrequency"));

        TableColumn statusColumn = new TableColumn("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        loansTable.getColumns().addAll(nameColumn, borrowerNameColumn, reasonColumn, loanSumColumn, totalLoanTimeColumn, interestColumn, paymentFrequencyColumn, statusColumn);
        Collection<LoanDto> loansDto = appController.getLoansDto();
        loansTable.setItems(null);
        ObservableList<LoanDto> listOfLoansDto = FXCollections.observableArrayList(loansDto);
        loansTable.setItems(listOfLoansDto);
    }

    @FXML
    public void makeAdminCustomersTable() {
        TableColumn nameColumn = new TableColumn("Customer Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn balanceColumn = new TableColumn("Balance");
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        TableColumn numOfPendingIngoingColumn = new TableColumn("#Pending loans \nas a loaner");
        numOfPendingIngoingColumn.setCellValueFactory(new PropertyValueFactory<>("numOfPendingIngoingLoans"));

        TableColumn numOfActiveIngoingColumn = new TableColumn("#Active loans \nas a loaner");
        numOfActiveIngoingColumn.setCellValueFactory(new PropertyValueFactory<>("numOfActiveIngoingLoans"));

        TableColumn numOfRiskIngoingColumn = new TableColumn("#Risk loans \nas a loaner");
        numOfRiskIngoingColumn.setCellValueFactory(new PropertyValueFactory<>("numOfRiskIngoingLoans"));

        TableColumn numOfFinishIngoingColumn = new TableColumn("#Finish loans \nas a loaner");
        numOfFinishIngoingColumn.setCellValueFactory(new PropertyValueFactory<>("numOfFinishIngoingLoans"));

        TableColumn numOfPendingOutgoingColumn = new TableColumn("#Pending loans \nas a borrower");
        numOfPendingOutgoingColumn.setCellValueFactory(new PropertyValueFactory<>("numOfPendingOutgoingLoans"));

        TableColumn numOfActiveOutgoingColumn = new TableColumn("#Active loans \nas a borrower");
        numOfActiveOutgoingColumn.setCellValueFactory(new PropertyValueFactory<>("numOfActiveOutgoingLoans"));

        TableColumn numOfRiskOutgoingColumn = new TableColumn("#Risk loans \nas a borrower");
        numOfRiskOutgoingColumn.setCellValueFactory(new PropertyValueFactory<>("numOfRiskOutgoingLoans"));

        TableColumn numOfFinishOutgoingColumn = new TableColumn("#Finish loans \nas a borrower");
        numOfFinishOutgoingColumn.setCellValueFactory(new PropertyValueFactory<>("numOfFinishOutgoingLoans"));

        appController.updateBankDtos();
        customersTable.getColumns().addAll(nameColumn, balanceColumn, numOfPendingIngoingColumn, numOfActiveIngoingColumn, numOfRiskIngoingColumn, numOfFinishIngoingColumn, numOfPendingOutgoingColumn, numOfActiveOutgoingColumn, numOfRiskOutgoingColumn, numOfFinishOutgoingColumn);
        Collection<CustomerDto> customersDto = appController.getCustomersDto();
        ObservableList<CustomerDto> listOfCustomersDto = FXCollections.observableArrayList(customersDto);
        customersTable.setItems(listOfCustomersDto);
    }

    @FXML
    public void showAdminScreen() {
        loansTable.getItems().clear();
        loansTable.getColumns().clear();
        customersTable.getColumns().clear();
        Platform.runLater(()-> {
                    makeAdminLoansTable();
                    makeAdminCustomersTable();
        });
    }

    public void moveTimeline(ActionEvent actionEvent) {
        appController.updateYaz();
    }

    public void setMainController(AppController appController) {
        this.appController = appController;
    }

    public void enableAndShowYazBtn() {
        increaseYazBtn.setDisable(false);
        increaseYazBtn.setVisible(true);
    }
}