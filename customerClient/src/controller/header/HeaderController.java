package controller.header;

import controller.app.CustomerAppController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HeaderController {
    @FXML
    private Label usernameLabel;

    @FXML
    private Label yazLabel;

    @FXML
    private CustomerAppController customerAppController;

    @FXML

    public void setMainController(CustomerAppController customerAppController) {
        this.customerAppController = customerAppController;
    }

    public void setName(String name) {
        Platform.runLater(() -> {
            usernameLabel.setText(name);
        });
    }

    public void setYazLabel(int yaz) {
        Platform.runLater(() -> {
            yazLabel.setText("YAZ: " + String.valueOf(yaz));
        });
    }

    public String getYazFromLabel() {
        return yazLabel.getText();
    }
}