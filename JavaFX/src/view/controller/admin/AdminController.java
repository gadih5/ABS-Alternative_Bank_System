package view.controller.admin;

import bank.CustomerDto;
import bank.LoanDto;
import bank.exception.*;
import bank.xml.XmlReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.controller.app.AppController;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Collection;

public class AdminController{
    @FXML
    private Button increaseYazBtn;
    @FXML
    private TableView<CustomerDto> customersTable;
    @FXML
    private TableView<LoanDto> loansTable;
    private AppController appController;
    FileChooser fileChooser = new FileChooser();

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


        loansTable.getColumns().addAll(nameColumn, borrowerNameColumn, reasonColumn, loanSumColumn, totalLoanTimeColumn, interestColumn, paymentFrequencyColumn, statusColumn);
        Collection<LoanDto> loansDto = appController.getLoansDto();
        loansTable.setItems(null);
        ObservableList<LoanDto> listOfLoansDto = FXCollections.observableArrayList(loansDto);
        loansTable.setItems(listOfLoansDto);
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
        loansTable.getItems().clear();
        makeAdminLoansTable();
        makeAdminCustomersTable();
        increaseYazBtn.setDisable(false);
    }


    @FXML
    void fileLoaderListener(ActionEvent event) {
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
        File file = fileChooser.showOpenDialog(new Stage());
        String filePath = file.getPath();
        if (filePath != null) {
            try {
                XmlReader myXml = new XmlReader(Paths.get(filePath));
                if(appController.loadXmlData(myXml.getDescriptor())) {
                    showAdminScreen();
                    appController.initYazLabel();
                    increaseYazBtn.setDisable(false);
                    appController.updatePathLabel(filePath);
                    appController.addUsers();
                    appController.setUserComboBoxEnable();
                    appController.updateBankDtos();
                }
            } catch (JAXBException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (NotXmlException e) {
                e.printStackTrace();
            }
        }
         else
            return;
    }

    public void moveTimeline(ActionEvent actionEvent) {
        appController.updateYaz();
    }

    public void setMainController(AppController appController) {
        this.appController = appController;
    }
}

