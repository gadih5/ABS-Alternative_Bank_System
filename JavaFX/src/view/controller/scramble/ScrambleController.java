package view.controller.scramble;

import bank.Customer;
import bank.CustomerDto;
import bank.Loan;
import bank.LoanDto;
import bank.exception.NegativeBalanceException;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import view.controller.customer.CustomerController;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ScrambleController {

    @FXML
    private Slider sumInvestSlider;

    @FXML
    private VBox categoriesVBox;

    @FXML
    private ScrollPane categoriesScrollPane;

    @FXML
    private AnchorPane categoriesAnchorPane;

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
    private Button investButton;

    @FXML
    private TableView<LoanDto> loansTable;

    private CustomerDto selectedCustomer;

    private CustomerController customerController;
    private int maxInterestPercent = 0;
    private int maxTotalYaz = 0;

    public void setMainController(CustomerController customerController) {
        this.customerController = customerController;
    }

    public void categoriesCheckBoxPressed(ActionEvent actionEvent) {
        if (categoriesCheckBox.isSelected())
            categoriesVBox.setDisable(false);
        else
            categoriesVBox.setDisable(true);
    }

    public void minInterestPercentCheckBoxPressed(ActionEvent actionEvent) {
        if (minInterestPercentCheckBox.isSelected())
            minInterestPercentSlider.setDisable(false);
        else
            minInterestPercentSlider.setDisable(true);
    }

    public void minTotalYazCheckBoxPressed(ActionEvent actionEvent) {
        if (minTotalYazCheckBox.isSelected())
            minTotalYazSlider.setDisable(false);
        else
            minTotalYazSlider.setDisable(true);
    }

    public void maxOpenLoansCheckBoxPressed(ActionEvent actionEvent) {
        if (maxOpenLoansCheckBox.isSelected())
            maxOpenLoansSlider.setDisable(false);
        else
            maxOpenLoansSlider.setDisable(true);
    }

    public void maxOwnerShipPercentCheckBoxPressed(ActionEvent actionEvent) {
        if (maxOwnerShipPercentCheckBox.isSelected())
            maxOwnershipPercentSlider.setDisable(false);
        else
            maxOwnershipPercentSlider.setDisable(true);
    }

    public void loadScrambleInfo(CustomerDto selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
        sumInvestSlider.setMax(selectedCustomer.getBalance());
        Set<String> categories = customerController.getCategories();
        for (String category : categories) {
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
        loansTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        investButton.setDisable(false);
        investButton.setVisible(true);
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
        interestColumn.setCellValueFactory(new PropertyValueFactory<>("interestPercent"));

        TableColumn paymentFrequencyColumn = new TableColumn("Payment Frequency");
        paymentFrequencyColumn.setCellValueFactory(new PropertyValueFactory<>("paymentFrequency"));

        TableColumn statusColumn = new TableColumn("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        loansTable.getColumns().addAll( nameColumn, borrowerNameColumn, reasonColumn, loanSumColumn, totalLoanTimeColumn, interestColumn, paymentFrequencyColumn, statusColumn);
        loansTable.getSelectionModel().selectionModeProperty().setValue(SelectionMode.MULTIPLE);
        loansTable.getSelectionModel().setSelectionMode (SelectionMode.MULTIPLE);
        loansTable.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
            Node node = evt.getPickResult().getIntersectedNode();

            // go up from the target node until a row is found or it's clear the
            // target node wasn't a node.
            while (node != null && node != loansTable && !(node instanceof TableRow)) {
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

        Set<String> chosenCategories = new HashSet<>();
        int categoriesChosed = -1;
        if (!categoriesVBox.isDisable()) {
            categoriesChosed = 1;
            for (int i = 0; i < categoriesVBox.getChildren().size(); i++) {
                CheckBox currCategory = (CheckBox) categoriesVBox.getChildren().get(i);
                if (currCategory.isSelected()) {
                    chosenCategories.add(currCategory.getText());
                }
            }
        }
        int minInterestPercent = -1;
        if (minInterestPercentCheckBox.isSelected())
            minInterestPercent = (int) minInterestPercentSlider.getValue();

        int minTotalYaz = -1;
        if (minTotalYazCheckBox.isSelected())
            minTotalYaz = (int) minTotalYazSlider.getValue();

        int maxOpenLoans = -1;
        if (maxOpenLoansCheckBox.isSelected())
            maxOpenLoans = (int) maxOpenLoansSlider.getValue();

        int maxOwnershipPercent = -1;
        if (maxOwnerShipPercentCheckBox.isSelected())
            maxOwnershipPercent = (int) maxOwnershipPercentSlider.getValue();


        Collection<LoanDto> loansDto = customerController.getLoansDto(categoriesChosed, chosenCategories, minInterestPercent, minTotalYaz, maxOpenLoans, maxOwnershipPercent, selectedCustomer);
        loansTable.setItems(null);
        ObservableList<LoanDto> listOfLoansDto = FXCollections.observableArrayList(loansDto);
        loansTable.setItems(listOfLoansDto);
    }

    public void investLoan(ActionEvent actionEvent) {

        ObservableList<LoanDto>list =  loansTable.getSelectionModel().getSelectedItems();
        ArrayList<LoanDto> listOfLoans = new ArrayList<>();
        listOfLoans.addAll(list);
        loansTable.getSelectionModel().clearSelection();

        if (listOfLoans.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error: No Loan Selected");
                alert.setContentText("Please make any selection, click OK and try again:");
                alert.showAndWait();
            } else {
            for (LoanDto loanDto : listOfLoans){
                double perLoanInvest = sumInvestSlider.getValue() / listOfLoans.size();
                try {
                    Loan loan = customerController.getSpecificLoan(loanDto.getLoanName());
                    Customer customer = customerController.getSpecificCustomer(selectedCustomer.getName());
                    if(perLoanInvest > loanDto.getAmountToComplete()){
                        perLoanInvest = loanDto.getAmountToComplete();
                        loan.addLoaner(customer, perLoanInvest);
                    }
                    else {
                        loan.addLoaner(customer, perLoanInvest);
                    }


                } catch (NegativeBalanceException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setHeaderText("Warning: Negative Balance");
                    alert.setContentText(selectedCustomer.getName() + " not have enough money in account balance");
                }
            }
            //TODO: give some indication that the investment done.. (cause this dont work :| )
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Info: Scramble Succeed!");
            alert.setContentText(selectedCustomer.getName() + " successfully invested all chosen loans.");
            }
        }
    }
