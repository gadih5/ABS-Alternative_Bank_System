package view.controller.admin;

import bank.CustomerDto;
import bank.Loan;
import bank.LoanDto;
import bank.exception.*;
import bank.xml.XmlReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.controller.app.AppController;
import view.controller.body.BodyController;
import view.controller.header.HeaderController;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import static view.controller.header.HeaderController.myBank;

public class AdminController implements BodyController {
    private AppController appController;
    FileChooser fileChooser = new FileChooser();


    @FXML
    private Button increase_yaz_btn;

    @FXML
    private TableView<CustomerDto> customers_table;

    @FXML
    private Button load_file_btn;

    @FXML
    private TableView<LoanDto> loans_table;


    @FXML
    void makeAdminLoansTable() {
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


        loans_table.getColumns().addAll(nameColumn, borrowerNameColumn, reasonColumn, loanSumColumn, totalLoanTimeColumn, interestColumn, paymentFrequencyColumn, statusColumn);
        Collection<LoanDto> loansDto = myBank.getLoansDto();
        loans_table.setItems(null);
        ObservableList<LoanDto> listOfLoansDto = FXCollections.observableArrayList(loansDto);
        loans_table.setItems(listOfLoansDto);
    }

    void makeAdminCustomersTable() {
        TableColumn nameColumn = new TableColumn("Customer Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn balanceColumn = new TableColumn("Balance");
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        TableColumn numOfPendingIngoingColumn = new TableColumn("#Pending loans \nas a loaner");
        numOfPendingIngoingColumn.setCellValueFactory(new PropertyValueFactory<>("numOfPendingIngoing"));

        TableColumn numOfActiveIngoingColumn = new TableColumn("#Active loans \nas a loaner");
        numOfActiveIngoingColumn.setCellValueFactory(new PropertyValueFactory<>("numOfActiveIngoing"));

        TableColumn numOfRiskIngoingColumn = new TableColumn("#Risk loans \nas a loaner");
        numOfRiskIngoingColumn.setCellValueFactory(new PropertyValueFactory<>("numOfRiskIngoing"));

        TableColumn numOfFinishIngoingColumn = new TableColumn("#Finish loans \nas a loaner");
        numOfFinishIngoingColumn.setCellValueFactory(new PropertyValueFactory<>("numOfFinishIngoing"));

        TableColumn numOfPendingOutgoingColumn = new TableColumn("#Pending loans \nas a borrower");
        numOfPendingOutgoingColumn.setCellValueFactory(new PropertyValueFactory<>("numOfPendingOutgoing"));

        TableColumn numOfActiveOutgoingColumn = new TableColumn("#Active loans \nas a borrower");
        numOfActiveOutgoingColumn.setCellValueFactory(new PropertyValueFactory<>("numOfActiveOutgoing"));

        TableColumn numOfRiskOutgoingColumn = new TableColumn("#Risk loans \nas a borrower");
        numOfRiskOutgoingColumn.setCellValueFactory(new PropertyValueFactory<>("numOfRiskOutgoing"));

        TableColumn numOfFinishOutgoingColumn = new TableColumn("#Finish loans \nas a borrower");
        numOfFinishOutgoingColumn.setCellValueFactory(new PropertyValueFactory<>("numOfFinishOutgoing"));


        customers_table.getColumns().addAll(nameColumn, balanceColumn, numOfPendingIngoingColumn, numOfActiveIngoingColumn, numOfRiskIngoingColumn, numOfFinishIngoingColumn, numOfPendingOutgoingColumn, numOfActiveOutgoingColumn, numOfRiskOutgoingColumn, numOfFinishOutgoingColumn);
        Collection<CustomerDto> customersDto = myBank.getCustomersDto();
        ObservableList<CustomerDto> listOfCustomersDto = FXCollections.observableArrayList(customersDto);
        customers_table.setItems(listOfCustomersDto);
    }

    @FXML
    public void showAdminScreen() {

        loans_table.getColumns().clear();
        customers_table.getColumns().clear();
        loans_table.getItems().clear();
        makeAdminLoansTable();
        makeAdminCustomersTable();
        increase_yaz_btn.setDisable(false);
    }


    @FXML
    void fileLoaderListener(ActionEvent event) {
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
        File file = fileChooser.showOpenDialog(new Stage());
        String filePath = file.getPath();
        if (filePath != null) {
            try {
                XmlReader myXml = null;
                try {
                    myXml = new XmlReader(Paths.get(filePath));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (NotXmlException e) {
                    e.printStackTrace();
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
                try {
                    try {
                        myBank.loadXmlData(myXml.getDescriptor());
                        showAdminScreen();
                        appController.updateYazLabel("YAZ: " + myBank.getSyncGlobalTimeUnit());
                        increase_yaz_btn.setDisable(false);
                        appController.updatePathLabel(filePath);
                        appController.addUsers();
                        appController.setUserComboboxEnable();
                    } catch (NegativeBalanceException e) {
                        e.printStackTrace();
                    } catch (UndefinedReasonException e) {
                        e.printStackTrace();
                    } catch (UndefinedCustomerException e) {
                        e.printStackTrace();
                    } catch (NegativeLoanSumException e) {
                        e.printStackTrace();
                    } catch (NegativeTotalTimeUnitException e) {
                        e.printStackTrace();
                    } catch (NegativeInterestPercentException e) {
                        e.printStackTrace();
                    } catch (NegativePaymentFrequencyException e) {
                        e.printStackTrace();
                    } catch (OverPaymentFrequencyException e) {
                        e.printStackTrace();
                    } catch (UndividedPaymentFrequencyException e) {
                        e.printStackTrace();
                    } catch (NotInCategoryException e) {
                        e.printStackTrace();
                    } catch (AlreadyExistCustomerException e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            return;
    }

    public void moveTimeline(ActionEvent actionEvent) {
        try {
            myBank.updateGlobalTimeUnit();
            appController.updateYazLabel("YAZ: " + String.valueOf(myBank.getSyncGlobalTimeUnit()));
        } catch (NegativeBalanceException e) {
            e.printStackTrace();
        }
    }


    public void setMainController(AppController appController) {
        this.appController = appController;
    }
}

