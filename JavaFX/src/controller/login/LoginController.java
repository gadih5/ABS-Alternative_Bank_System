package controller.login;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;
import thirdParty.okHttp.*;
import controller.constants.Constants;
import okhttp3.*;
import utils.HttpClientUtil;

import java.io.IOException;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField nameTF;

    @FXML
    private Button loginBtn;

    @FXML
    void loginClicked(ActionEvent event) {
        String userName = nameTF.getText();
        if (userName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Warning: User name is empty");
            alert.setContentText("You can't login with empty user name, click OK and try again:");
            alert.showAndWait();
        }

        String finalUrl = HttpUrl
                .parse(Constants.LOGIN_PAGE)
                .newBuilder()
                .addQueryParameter("username", userName)
                .build()
                .toString();

        updateHttpStatusLine("New request is launched for: " + finalUrl);

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() ->
                        errorMessageProperty.set("Something went wrong: " + e.getMessage())
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() ->
                            errorMessageProperty.set("Something went wrong: " + responseBody)
                    );
                } else {
                    Platform.runLater(() -> {
                        chatAppMainController.updateUserName(userName);
                        chatAppMainController.switchToChatRoom();
                    });
                }
            }
        });

    }
    private void updateHttpStatusLine(String data) {
        chatAppMainController.updateHttpLine(data);
    }

}
