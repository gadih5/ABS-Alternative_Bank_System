package view.controller.scramble;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import view.controller.customer.CustomerController;

public class ScrambleController {

    @FXML
    private Slider sumInvestSlider;

    @FXML
    private Label sumInvestLabel;

    @FXML
    private CustomerController customerController;

    public void setMainController(CustomerController customerController) {
        this.customerController = customerController;
    }
}