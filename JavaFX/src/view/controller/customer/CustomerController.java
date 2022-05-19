package view.controller.customer;



import bank.CustomerDto;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import view.controller.admin.AdminController;
import view.controller.app.AppController;
import view.controller.information.InformationController;
import view.controller.payment.PaymentController;
import view.controller.scramble.ScrambleController;

import java.util.Collection;

import static view.controller.header.HeaderController.myBank;

public class CustomerController {
    private AppController appController;

    @FXML
    private SplitPane paymentComponent;
    @FXML
    private PaymentController paymentComponentController;
    @FXML
    private SplitPane informationComponent;
    @FXML
    private InformationController informationComponentController;
    @FXML
    private GridPane scrambleComponent;
    @FXML
    private ScrambleController scrambleComponentController;


    @FXML
    private AdminController adminComponentController;


    public void setPaymentComponentController(PaymentController paymentComponentController) {
        this.paymentComponentController = paymentComponentController;
        paymentComponentController.setMainController(this);
    }

    public void setInformationComponentController(InformationController informationComponentController) {
        this.informationComponentController = informationComponentController;
        informationComponentController.setMainController(this);
    }

    public void setScrambleComponentController(ScrambleController scrambleComponentController) {
        this.scrambleComponentController = scrambleComponentController;
        scrambleComponentController.setMainController(this);
    }
    @FXML
    public void initialize() {
        if (informationComponentController != null && paymentComponentController != null && scrambleComponentController != null) {
            informationComponentController.setMainController(this);
            paymentComponentController.setMainController(this);
            scrambleComponentController.setMainController(this);
        }
    }

    public void setMainController(AppController appController) {
        this.appController = appController;
    }

    public void loadCustomerDetails(String userName) {
        Collection<CustomerDto> customersDto = myBank.getCustomersDto();
        CustomerDto selectedCustomer = null;
        for(CustomerDto customerDto : customersDto){
            if(customerDto.getName().equals(userName))
                selectedCustomer = customerDto;
        }
        if(selectedCustomer == null)
            return;


        informationComponentController.showInfoTable(selectedCustomer);

    }
}
