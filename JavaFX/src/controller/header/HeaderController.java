package controller.header;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
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

   /* @FXML
    public void updatePathLabel(String s){
        pathLabel.setText(s);
    }
*/
    public void updateYazLabel(String yaz) {
        yazLabel.setText("YAZ: " + yaz);
    }

 /*   public void addUserBtn(String user) {
        userComboBox.getItems().add(user);
    }
*/
/*    public void chooseUser(ActionEvent actionEvent) {
        String userName = userComboBox.getSelectionModel().getSelectedItem();
        if(userName == null)
            return;
        else
            appController.changeBody(userName);
    }*/

  /*  public void setUserComboBoxEnable() {
        userComboBox.setDisable(false);
    }

    public void addAdminBtn() {
        userComboBox.getItems().add("Admin");
        userComboBox.getSelectionModel().select(0);
        userComboBox.setDisable(true);
    }*/
/*

    public void removeAllUsers() {
        userComboBox.getItems().clear();
    }
*/


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