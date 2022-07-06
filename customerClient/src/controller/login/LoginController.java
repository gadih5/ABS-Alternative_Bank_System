package controller.login;

import controller.app.AppController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.jetbrains.annotations.NotNull;
import controller.constants.Constants;
import okhttp3.*;
import utils.HttpClientUtil;
import java.io.IOException;
import java.io.IOException;
import java.net.URL;

public class LoginController {
    @FXML
    private AnchorPane bodyAnchorPane;
    @FXML
    private TextField nameTF;
    @FXML
    private Label doubleError;
    @FXML
    private ScrollPane appComponent;
    @FXML
    private Button loginBtn;
    private AppController appController;

    public void setMainController(AppController appController) {
        this.appController = appController;
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

            // updateHttpStatusLine("New request is launched for: " + finalUrl);
            System.out.println("FROM CLIENT: " + finalUrl);
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
                    if (response.code() != 200) {
                        String responseBody = response.body().string();
                        doubleError.setVisible(true);
                    } else {
                        Platform.runLater(() -> {
                            URL url = getClass().getResource("/controller/app/app.fxml");
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(url);
                            try {
                                appComponent = fxmlLoader.load(url.openStream());
                                appController = fxmlLoader.getController();
                                bodyAnchorPane.getChildren().set(0, appComponent);
                                AnchorPane.setBottomAnchor(appComponent, 0.0);
                                AnchorPane.setLeftAnchor(appComponent, 0.0);
                                AnchorPane.setRightAnchor(appComponent, 0.0);
                                AnchorPane.setTopAnchor(appComponent, 0.0);
                                appController.updateUserName(userName);
                                appController.changeBody(userName);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                }
            });
        }
    }
/*    private void updateHttpStatusLine(String data) {
        chatAppMainController.updateHttpLine(data);
    }*/

}
