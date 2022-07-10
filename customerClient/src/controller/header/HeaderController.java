package controller.header;

import controller.app.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class HeaderController {
    @FXML
    private Label pathLabel;
    @FXML
    private Label yazLabel;
    @FXML
    private ComboBox<String> userComboBox;
    @FXML
    private AppController appController;
    @FXML

    public void setMainController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    public void updatePathLabel(String s){
        pathLabel.setText(s);
    }

    public void updateYazLabel(String yaz) {
        yazLabel.setText("YAZ: " + yaz);
    }

    public void addUserBtn(String user) {
        userComboBox.getItems().add(user);
    }

    public void chooseUser(ActionEvent actionEvent) {
        String userName = userComboBox.getSelectionModel().getSelectedItem();
        if(userName == null)
            return;
        else
            appController.changeBody(userName);
    }

    public void setUserComboBoxEnable() {
        userComboBox.setDisable(false);
    }

    public void addAdminBtn() {
        userComboBox.getItems().add("Admin");
        userComboBox.getSelectionModel().select(0);
        userComboBox.setDisable(true);
    }

    public void removeAllUsers() {
        userComboBox.getItems().clear();
    }

    public void initYazLabel() {
        yazLabel.setText("YAZ: 1");
    }
}