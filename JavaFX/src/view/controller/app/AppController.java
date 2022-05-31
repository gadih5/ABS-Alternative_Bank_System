package view.controller.app;

import bank.*;
import bank.exception.*;
import bank.xml.generated.AbsDescriptor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.controller.admin.AdminController;
import view.controller.customer.CustomerController;
import view.controller.header.HeaderController;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AppController {
    @FXML
    private HBox headerComponent;
    @FXML
    private HeaderController headerComponentController;
    @FXML
    private VBox adminComponent;
    @FXML
    private AdminController adminComponentController;
    @FXML
    private TabPane customerComponent;
    @FXML
    private CustomerController customerComponentController;
    @FXML
    private AnchorPane bodyAnchorPane;
    private Bank myBank = new Bank();

    @FXML
    public void initialize() {
        if (headerComponentController != null && adminComponentController != null) {
            headerComponentController.setMainController(this);
            adminComponentController.setMainController(this);
        }
    }

    public void setHeaderComponentController(HeaderController headerComponentController) {
        this.headerComponentController = headerComponentController;
        headerComponentController.setMainController(this);
    }

    public void setAdminComponentController(AdminController adminComponentController) {
        this.adminComponentController = adminComponentController;
        adminComponentController.setMainController(this);
    }

    public void setCustomerComponentController(CustomerController customerComponentController) {
        this.customerComponentController = customerComponentController;
        customerComponentController.setMainController(this);
    }

    public void updatePathLabel(String filePath) {
        headerComponentController.updatePathLabel(filePath);
    }

    public void updateYaz() {
        try {
            myBank.updateGlobalTimeUnit();
            headerComponentController.updateYazLabel(myBank.getSyncGlobalTimeUnit());

        } catch (NegativeBalanceException e) {
            //TODO in the new version the customers actively pays their payments and debts,
            // so maybe we need to change the way of the bank so that not throw negative balance and updateYaz of bank will not pay automatically payments
            e.printStackTrace();
        }

    }

    public void changeBody(String userName) {
        if (userName.equals("Admin")) {
            bodyAnchorPane.getChildren().set(0, adminComponent);
            AnchorPane.setBottomAnchor(adminComponent, 0.0);
            AnchorPane.setLeftAnchor(adminComponent, 0.0);
            AnchorPane.setRightAnchor(adminComponent, 0.0);
            AnchorPane.setTopAnchor(adminComponent, 0.0);
            adminComponentController.showAdminScreen();
        } else {
            try {
                URL url = getClass().getResource("../customer/customer.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(url);
                customerComponent = fxmlLoader.load(url.openStream());
                customerComponentController = fxmlLoader.getController();
                bodyAnchorPane.getChildren().set(0, customerComponent);
                AnchorPane.setBottomAnchor(customerComponent, 0.0);
                AnchorPane.setLeftAnchor(customerComponent, 0.0);
                AnchorPane.setRightAnchor(customerComponent, 0.0);
                AnchorPane.setTopAnchor(customerComponent, 0.0);
                setCustomerComponentController(customerComponentController);
                customerComponentController.loadCustomerDetails(userName);
                //TODO load scramble tab & payment tab
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addUsers() {
        headerComponentController.removeAllUsers();
        headerComponentController.addAdminBtn();
        for (bank.Customer customer : myBank.getCustomers()) {
            String name = customer.getName();
            headerComponentController.addUserBtn(name);
        }
    }

    public void setUserComboBoxEnable() {
        headerComponentController.setUserComboBoxEnable();
    }

    public boolean loadXmlData(AbsDescriptor descriptor) {
        boolean res = false;
        Bank newBank = new Bank();
        try {
            newBank.loadXmlData(descriptor);
            myBank = newBank;
            res = true;
        } catch (NegativeBalanceException e) {
            showErrorAlert("Negative Balance!");
        } catch (UndefinedReasonException e) {
            showErrorAlert("Undefined Reason!");
        } catch (UndefinedCustomerException e) {
            showErrorAlert("Undefined Customer!");
        } catch (NegativeLoanSumException e) {
            showErrorAlert("Negative Loan Sum!");
        } catch (NegativeTotalTimeUnitException e) {
            showErrorAlert("Negative Total YAZ!");
        } catch (NegativeInterestPercentException e) {
            showErrorAlert("Negative Interest Percent!");
        } catch (NegativePaymentFrequencyException e) {
            showErrorAlert("Negative Payment Frequency!");
        } catch (OverPaymentFrequencyException e) {
            showErrorAlert("Over Payment Frequency!");
        } catch (UndividedPaymentFrequencyException e) {
            showErrorAlert("Undivided Payment Frequency!");
        } catch (NotInCategoryException e) {
            showErrorAlert("Category is Missing!");
        } catch (AlreadyExistCustomerException e) {
            showErrorAlert("Already Exist Customer!");
        } finally {
            return res;
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error: " + message);
        alert.setContentText("Cant load this file, please try another file.");
        alert.showAndWait();
    }

    public Collection<LoanDto> getLoansDto() {
        return myBank.getLoansDto();
    }

    public Collection<CustomerDto> getCustomersDto() {
        return myBank.getCustomersDto();
    }

    public Collection<Customer> getCustomers() {
        return myBank.getCustomers();
    }

    public void initYazLabel() {
        headerComponentController.initYazLabel();
    }

    public void eraseBank() {
        myBank = new Bank();
    }

    public Set<String> getCategories() {
        return myBank.getCategory();
    }

    public int calcMaxInterestPercent(CustomerDto selectedCustomer) {
        int res = 0;
        for (LoanDto loanDto : myBank.getLoansDto()) {
            if (loanDto.getInterestPercent() > res && !(loanDto.getBorrowerName().equals(selectedCustomer.getName())))
                res = loanDto.getInterestPercent();
        }
        return res;
    }

    public int calcMaxTotalYaz(CustomerDto selectedCustomer) {
        int res = 0;
        for (LoanDto loanDto : myBank.getLoansDto()) {
            if (loanDto.getTotalTimeUnit() > res && !(loanDto.getBorrowerName().equals(selectedCustomer.getName())))
                res = loanDto.getTotalTimeUnit();
        }
        return res;
    }

    public int getNumOfLoans() {
        return myBank.getLoans().size();
    }

    public Collection<LoanDto> getLoansDtoForScramble(int categoriesChosed, Set<String> chosenCategories, int minInterestPercent, int minTotalYaz, int maxOpenLoans, int maxOwnershipPercent, CustomerDto selectedCustomer) {
        Set<LoanDto> validLoans = new HashSet<>();
        for (LoanDto loanDto : myBank.getLoansDto()) {
            if (!(loanDto.getBorrowerName().equals(selectedCustomer.getName()))) {
                if (chosenCategories.contains(loanDto.getReason()) || categoriesChosed == -1) {
                    if (loanDto.getInterestPercent() <= minInterestPercent || minInterestPercent == -1) {
                        if (loanDto.getTotalTimeUnit() <= minTotalYaz || minTotalYaz == -1) {
                            if ((loanDto.getRemainFund()) / (loanDto.getLoanSum()) <= maxOwnershipPercent || maxOwnershipPercent == -1) {
                                if (maxOpenLoans == -1)
                                    validLoans.add(loanDto);
                                else {
                                    Customer borrower = null;
                                    for (Customer customer : myBank.getCustomers()) {
                                        if (customer.getName().equals(loanDto.getBorrowerName())) {
                                            borrower = customer;
                                            break;
                                        }
                                    }
                                    if (borrower != null) {
                                        Set<LoanDto> loans = new HashSet<>();
                                        for (Loan loan : myBank.getLoans()) {
                                            if (loan.getStatus() != Status.Finished && loan.getBorrower().getName().equals(borrower.getName())) {
                                                loans.add(loan.getLoanDto());
                                            }
                                        }
                                        if (loans.size() <= maxOpenLoans) {
                                            validLoans.add(loanDto);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    return validLoans;
    }

    public void updateBankDtos() {
        myBank.updateAllDtos();
    }

    public Loan getSpecificLoan(String loanName) {
        Loan resLoan = null;
        for(Loan loan : myBank.getLoans()){
            if(loan.getLoanName().equals(loanName)){
                resLoan = loan;
            }
        }
        return resLoan;
    }

    public Customer getSpecificCustomer(String name) {
        Customer resCustomer = null;
        for(Customer customer : myBank.getCustomers()){
            if(customer.getName().equals(name)){
                resCustomer = customer;
            }
        }
        return resCustomer;
    }
}