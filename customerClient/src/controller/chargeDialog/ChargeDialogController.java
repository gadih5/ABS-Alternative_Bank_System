package controller.chargeDialog;

import controller.information.InformationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChargeDialogController {
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField amountTf;
    @FXML
    private SplitPane informationComponent;
    @FXML
    private InformationController informationComponentController;
    @FXML
    void cancelClicked(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void setMainController(InformationController informationComponentController) {
        this.informationComponentController = informationComponentController;
    }

    @FXML
    public void applyCharge(ActionEvent actionEvent) {
        informationComponentController.chargeAmount(amountTf.getText());
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void focusTextField() {
        amountTf.requestFocus();
    }
}