package controller.login;

import controller.app.CustomerAppController;
import controller.constants.Constants;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.HttpClientUtil;
import java.io.IOException;
import java.net.URL;

public class CustomerLoginController {
    @FXML
    private AnchorPane bodyAnchorPane;
    @FXML
    private TextField nameTF;
    @FXML
    private Label doubleError;
    @FXML
    private ScrollPane customerAppComponent;
    @FXML
    private CustomerAppController customerAppComponentController;
    @FXML
    private ScrollPane customerLoginScrollPane;

    public void setMainController(CustomerAppController customerAppController) {
        this.customerAppComponentController = customerAppController;
    }

    @FXML
    void onClick(ActionEvent event) {
        String userName = nameTF.getText();
        if (userName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Warning: User name is empty");
            alert.setContentText("You can't login with empty user name, click OK and try again:");
            alert.showAndWait();
        }
        else {
            String finalUrl = HttpUrl
                    .parse(Constants.LOGIN_PAGE)
                    .newBuilder()
                    .addQueryParameter("username", userName)
                    .build()
                    .toString();

            HttpClientUtil.runAsync(finalUrl, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setHeaderText("Warning: Something went wrong");
                    alert.setContentText("Click OK and try again:");
                    alert.showAndWait();
                }
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    response.body().close();
                    if (response.code() != 200) {
                        doubleError.setVisible(true);
                    } else {
                        Platform.runLater(() -> {
                            URL url = getClass().getResource("/controller/app/customerApp.fxml");
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(url);
                            try {
                                customerAppComponent = fxmlLoader.load(url.openStream());
                                customerAppComponentController = fxmlLoader.getController();
                                bodyAnchorPane.getChildren().set(0, customerAppComponent);
                                AnchorPane.setBottomAnchor(customerAppComponent, 0.0);
                                AnchorPane.setLeftAnchor(customerAppComponent, 0.0);
                                AnchorPane.setRightAnchor(customerAppComponent, 0.0);
                                AnchorPane.setTopAnchor(customerAppComponent, 0.0);
                                customerAppComponentController.updateUserName(userName, "false");
                                customerAppComponentController.changeBody(userName);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                }
            });
        }
    }
}