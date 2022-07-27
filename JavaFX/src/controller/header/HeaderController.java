package controller.header;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import controller.app.AppController;

public class HeaderController {

    @FXML
    private Label yazLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private AppController appController;

    @FXML
    public void setMainController(AppController appController) {
        this.appController = appController;
    }

    public void updateYazLabel(String yaz) {
        yazLabel.setText("YAZ: " + yaz);
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
}