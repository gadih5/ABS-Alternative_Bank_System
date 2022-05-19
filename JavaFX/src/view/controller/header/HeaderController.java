package view.controller.header;

import bank.Bank;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import view.controller.admin.AdminController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import view.controller.app.AppController;


public class HeaderController {


    @FXML
    private Label path_label;

    @FXML
    private Label yaz_label;

    @FXML
    private ComboBox<String> userComboBox;



    private AppController appController;
    static public Bank myBank=new Bank();
    @FXML private AdminController adminController;
    FXMLLoader fxmlLoader = new FXMLLoader();


    @FXML
    public void updatePathLabel(String s){
        path_label.setText(s);
    }

    public void updateYazLabel(String s) {
        yaz_label.setText(s);
    }

    public void setMainController(AppController appController) {
        this.appController = appController;
    }

    public void addUserBtn(String user) {
        userComboBox.getItems().add(user);
    }

    public void chooseUser(ActionEvent actionEvent) {
        String userName = userComboBox.getSelectionModel().getSelectedItem();
        if(userName == null)
            return;
        if(userName.equals("Admin")){
        }
        else{
            appController.changeBody(userName);
        }


    }

    public void setUserComboboxEnable() {
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
}