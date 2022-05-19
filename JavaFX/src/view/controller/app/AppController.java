package view.controller.app;

import bank.CustomerDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.controller.admin.AdminController;
import view.controller.customer.CustomerController;
import view.controller.header.HeaderController;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import static view.controller.header.HeaderController.myBank;

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
    @FXML
    public void initialize() {
        if (headerComponentController != null && adminComponentController != null) {
            headerComponentController.setMainController(this);
            adminComponentController.setMainController(this);
        }
    }

    public void updatePathLabel(String filePath) {
        headerComponentController.updatePathLabel(filePath);
    }

    public void updateYazLabel(String s) {
        headerComponentController.updateYazLabel(s);
    }


    public void changeBody(String userName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../customer/customer.fxml"));
            customerComponent = loader.load(Objects.requireNonNull(getClass().getResource("../customer/customer.fxml")));
            loader.load();
            bodyAnchorPane.getChildren().set(0,customerComponent);
            AnchorPane.setBottomAnchor(customerComponent,0.0);
            AnchorPane.setLeftAnchor(customerComponent,0.0);
            AnchorPane.setRightAnchor(customerComponent,0.0);
            AnchorPane.setTopAnchor(customerComponent,0.0);
            customerComponentController =loader.getController();

            customerComponentController.loadCustomerDetails(userName);

            setCustomerComponentController(customerComponentController);
            customerComponentController.setMainController(this);
            customerComponentController.initialize();

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

    public void setUserComboboxEnable() {
        headerComponentController.setUserComboboxEnable();
    }
}