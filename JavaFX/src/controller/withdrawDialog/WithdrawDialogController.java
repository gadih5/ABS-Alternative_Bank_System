package controller.withdrawDialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import controller.information.InformationController;

public class WithdrawDialogController {
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField amountTf;
    @FXML
    private InformationController informationComponentController;

    public void setMainController(InformationController informationComponentController) {
        this.informationComponentController = informationComponentController;
    }

    @FXML
    void applyWithdraw(ActionEvent event) {
        informationComponentController.withdrawAmount(amountTf.getText());
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void cancelClicked(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void focusTextField() {
        amountTf.requestFocus();
    }
}


