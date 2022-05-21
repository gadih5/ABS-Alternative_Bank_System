package view.controller.app;

import bank.Bank;
import bank.Customer;
import bank.CustomerDto;
import bank.LoanDto;
import bank.exception.*;
import bank.xml.generated.AbsDescriptor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("../customer/customer.fxml");
            fxmlLoader.setLocation(url);
            customerComponent = fxmlLoader.load(url.openStream());
            customerComponentController = fxmlLoader.getController();
            bodyAnchorPane.getChildren().set(0,customerComponent);
            AnchorPane.setBottomAnchor(customerComponent,0.0);
            AnchorPane.setLeftAnchor(customerComponent,0.0);
            AnchorPane.setRightAnchor(customerComponent,0.0);
            AnchorPane.setTopAnchor(customerComponent,0.0);
            setCustomerComponentController(customerComponentController);
            customerComponentController.loadCustomerDetails(userName);
            //TODO load scramble tab & payment tab
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUsers() {
        headerComponentController.removeAllUsers();
        headerComponentController.addAdminBtn();
        for(bank.Customer customer: myBank.getCustomers()){
            String name = customer.getName();
            headerComponentController.addUserBtn(name);
        }
    }

    public void setUserComboBoxEnable() {
        headerComponentController.setUserComboBoxEnable();
    }

    public void loadXmlData(AbsDescriptor descriptor) {
        try {
            myBank.loadXmlData(descriptor);
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
}