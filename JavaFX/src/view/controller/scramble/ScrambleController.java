package view.controller.scramble;

import bank.CustomerDto;
import bank.LoanDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import view.controller.customer.CustomerController;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ScrambleController {

    @FXML
    private Slider sumInvestSlider;

    @FXML
    private VBox categoriesVBox;

    @FXML
    private Slider maxOwnershipPercentSlider;

    @FXML
    private CheckBox categoriesCheckBox;

    @FXML
    private CheckBox minInterestPercentCheckBox;

    @FXML
    private CheckBox minTotalYazCheckBox;

    @FXML
    private CheckBox maxOpenLoansCheckBox;

    @FXML
    private CheckBox maxOwnerShipPercentCheckBox;

    @FXML
    private TextField sumInvestTextField;

    @FXML
    private Slider minInterestPercentSlider;

    @FXML
    private Slider minTotalYazSlider;

    @FXML
    private Slider maxOpenLoansSlider;

    @FXML
    private TextField minInterestPercentTextField;

    @FXML
    private TextField minTotalYazTextField;

    @FXML
    private TextField maxOpenLoansTextField;

    @FXML
    private Button applyButton;

    @FXML
    private TextField maxOwnerShipPercentTextField;

    @FXML
    private TableView<LoanDto> loansTable;

    private CustomerDto selectedCustomer;

    private CustomerController customerController;
    private int maxInterestPercent=0;
    private int maxTotalYaz=0;

    public void setMainController(CustomerController customerController) {
        this.customerController = customerController;
    }

    public void categoriesCheckBoxPressed(ActionEvent actionEvent) {
        if(categoriesCheckBox.isSelected())
            categoriesVBox.setDisable(false);
        else
            categoriesVBox.setDisable(true);
    }

    public void minInterestPercentCheckBoxPressed(ActionEvent actionEvent) {
        if(minInterestPercentCheckBox.isSelected())
            minInterestPercentSlider.setDisable(false);
        else
            minInterestPercentSlider.setDisable(true);
    }

    public void minTotalYazCheckBoxPressed(ActionEvent actionEvent) {
        if(minTotalYazCheckBox.isSelected())
            minTotalYazSlider.setDisable(false);
        else
            minTotalYazSlider.setDisable(true);
    }

    public void maxOpenLoansCheckBoxPressed(ActionEvent actionEvent) {
        if(maxOpenLoansCheckBox.isSelected())
            maxOpenLoansSlider.setDisable(false);
        else
            maxOpenLoansSlider.setDisable(true);
    }

    public void maxOwnerShipPercentCheckBoxPressed(ActionEvent actionEvent) {
        if(maxOwnerShipPercentCheckBox.isSelected())
            maxOwnershipPercentSlider.setDisable(false);
        else
            maxOwnershipPercentSlider.setDisable(true);
    }

    public void loadScrambleInfo(CustomerDto selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
        sumInvestSlider.setMax(selectedCustomer.getBalance());
        Set<String> categories = customerController.getCategories();
        for(String category:categories) {
            categoriesVBox.getChildren().add(new CheckBox(category));
        }
        maxInterestPercent = customerController.calcMaxInterestPercent(selectedCustomer);
        minInterestPercentSlider.setMax(maxInterestPercent);

        maxTotalYaz = customerController.calcMaxTotalYaz(selectedCustomer);
        minTotalYazSlider.setMax(maxTotalYaz);

        maxOpenLoansSlider.setMax(customerController.getNumOfLoans());
    }

    public void changeSumInvest(MouseEvent mouseEvent) {
        sumInvestTextField.textProperty().setValue(String.valueOf(sumInvestSlider.valueProperty().intValue()));
        applyButton.setDisable(false);
    }

    public void changeInterestPercent(MouseEvent mouseEvent) {
        minInterestPercentTextField.textProperty().setValue(String.valueOf(minInterestPercentSlider.valueProperty().intValue()));
    }

    public void changeTotalYaz(MouseEvent mouseEvent) {
        minTotalYazTextField.textProperty().setValue(String.valueOf(minTotalYazSlider.valueProperty().intValue()));
    }

    public void changeLoanersOpenLoans(MouseEvent mouseEvent) {
        maxOpenLoansTextField.textProperty().setValue(String.valueOf(maxOpenLoansSlider.valueProperty().intValue()));
    }

    public void changeOwnershipPercent(MouseEvent mouseEvent) {
        maxOwnerShipPercentTextField.textProperty().setValue(String.valueOf(maxOwnershipPercentSlider.valueProperty().intValue()));
    }

    public void applyButtonClicked(ActionEvent actionEvent) {
        loansTable.getItems().clear();
        loansTable.getColumns().clear();
        showValidLoansInTable();






    }

    private void showValidLoansInTable() {
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
        Set<String> chosenCategories = new HashSet<>();
        if(!categoriesVBox.isDisable()) {
            for(int i=0;i<categoriesVBox.getChildren().size();i++){
                CheckBox currCategory = (CheckBox)categoriesVBox.getChildren().get(i);
                if(currCategory.isSelected()){
                    chosenCategories.add(currCategory.getText());
                }
            }
        }
        int minInterestPercent= -1;
        if(minInterestPercentCheckBox.isSelected())
            minInterestPercent = (int)minInterestPercentSlider.getValue();

        int minTotalYaz= -1;
        if(minTotalYazCheckBox.isSelected())
            minTotalYaz = (int)minTotalYazSlider.getValue();

        int maxOpenLoans = -1;
        if(maxOpenLoansCheckBox.isSelected())
            maxOpenLoans = (int)maxOpenLoansSlider.getValue();

        int maxOwnershipPercent = -1;
        if(maxOwnerShipPercentCheckBox.isSelected())
            maxOwnershipPercent = (int)maxOwnershipPercentSlider.getValue();



        Collection<LoanDto> loansDto = customerController.getLoansDto((int)sumInvestSlider.getValue(), chosenCategories, minInterestPercent, minTotalYaz, maxOpenLoans, maxOwnershipPercent, selectedCustomer);
        loansTable.setItems(null);
        ObservableList<LoanDto> listOfLoansDto = FXCollections.observableArrayList(loansDto);
        loansTable.setItems(listOfLoansDto);

    }
}