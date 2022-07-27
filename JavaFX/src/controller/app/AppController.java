package controller.app;

import _json.*;
import bank.*;
import com.google.gson.JsonSyntaxException;
import controller.admin.AdminController;
import controller.constants.Constants;
import controller.header.HeaderController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.HttpClientUtil;
import java.io.IOException;
import java.util.*;

public class AppController {
    @FXML
    private GridPane headerComponent;
    @FXML
    private HeaderController headerComponentController;
    @FXML
    private VBox adminComponent;
    @FXML
    private AdminController adminComponentController;
    @FXML
    private TabPane customerComponent;
    @FXML
    private AnchorPane bodyAnchorPane;

    private String username;

    @FXML
    public void initialize() {
        if (headerComponentController != null && adminComponentController != null) {
            headerComponentController.setMainController(this);
            adminComponentController.setMainController(this);
        }
    }

    public void setHeaderComponentController(HeaderController headerComponentController) {
        this.headerComponentController = headerComponentController;
        headerComponentController.setMainController(this);
    }

    public void setAdminComponentController(AdminController adminComponentController) {
        this.adminComponentController = adminComponentController;
        adminComponentController.setMainController(this);
    }

    public void updateYaz() {
        Request request = new Request.Builder()
                .url(Constants.UPDATE_YAZ_PAGE)
                .build();

        Call call = Constants.HTTP_CLIENT.newCall(request);
        try {
            Response response = call.execute();
            String resp = response.body().string();
            response.body().close();
            if (response.code() != 200) {

            } else {
                Platform.runLater(() -> {
                    headerComponentController.updateYazLabel(resp);
                    adminComponentController.showAdminScreen();
                });
            }

        } catch (IOException e) {
            System.out.println("Error when trying to get data. Exception: " + e.getMessage());
        }
    }

    public void changeBody(String userName) {
        String finalUrl = HttpUrl
                .parse(Constants.IS_ADMIN)
                .newBuilder()
                .addQueryParameter("userName", userName)
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
                        } else {
                            Platform.runLater(()-> {
                                bodyAnchorPane.getChildren().set(0, adminComponent);
                                AnchorPane.setBottomAnchor(adminComponent, 0.0);
                                AnchorPane.setLeftAnchor(adminComponent, 0.0);
                                AnchorPane.setRightAnchor(adminComponent, 0.0);
                                AnchorPane.setTopAnchor(adminComponent, 0.0);
                                headerComponentController.setName(userName);
                                getYazValueFromBank();
                                adminComponentController.enableAndShowYazBtn();
                                Platform.runLater(()-> {
                                    adminComponentController.showAdminScreen();
                                });
                            });
                        }
                    }
                });
    }

    private int getYazValueFromBank() {
        int yaz=0;
        Request request = new Request.Builder()
                .url(Constants.GET_YAZ)
                .build();

        Call call = Constants.HTTP_CLIENT.newCall(request);
        try {
            Response response = call.execute();
            String resp = response.body().string();
            response.body().close();
            yaz = Constants.GSON_INSTANCE.fromJson(resp, Integer.class);
            headerComponentController.setYazLabel(yaz);
        } catch (IOException e) {
            System.out.println("Error when trying to get data. Exception: " + e.getMessage());
        }
        return yaz;
    }

    public ArrayList<LoanDto> getLoansDto() {
        ArrayList<LoanDto> loanDtos = new ArrayList<>();
        Request request = new Request.Builder()
                .url(Constants.GET_LOANS_DTO)
                .build();

        Call call = Constants.HTTP_CLIENT.newCall(request);
        try {
            Response response = call.execute();
            String resp = response.body().string();
            response.body().close();
            LoanDtoList_json loanDtoList_json = Constants.GSON_INSTANCE.fromJson(resp, LoanDtoList_json.class);
            loanDtos = loanDtoList_json.loansDtos;
        } catch (IOException e) {
            System.out.println("Error when trying to get data. Exception: " + e.getMessage());
        }
        return loanDtos;
    }

    public ArrayList<CustomerDto> getCustomersDto() {
        ArrayList<CustomerDto> customerDtos = new ArrayList<>();
        Request request = new Request.Builder()
                .url(Constants.GET_CUSTOMERS_DTO)
                .build();

        Call call = Constants.HTTP_CLIENT.newCall(request);
        try {
            Response response = call.execute();
            String resp = response.body().string();
            response.body().close();
            CustomerDtoList_json customerDtoList_json = Constants.GSON_INSTANCE.fromJson(resp, CustomerDtoList_json.class);
            customerDtos = customerDtoList_json.customersDtos;
        } catch (IOException e) {
            System.out.println("Error when trying to get data. Exception: " + e.getMessage());
        }catch(JsonSyntaxException e){
        }
        return customerDtos;
    }

    public ArrayList<Customer> getCustomers() {
        @Nullable ArrayList<Customer> customers = new ArrayList<>();
        Request request = new Request.Builder()
                .url(Constants.GET_CUSTOMERS)
                .build();

        Call call = Constants.HTTP_CLIENT.newCall(request);
        try {
            Response response = call.execute();
            CustomerList_json customerList_json;
            String resp = response.body().string();
            response.body().close();
            if(resp != null) {
                customerList_json = Constants.GSON_INSTANCE.fromJson(resp, CustomerList_json.class);
                if(customerList_json.customers != null)
                    customers = customerList_json.customers;
            }
        } catch (IOException e) {
            System.out.println("Error when trying to get data. Exception: " + e.getMessage());
        }
        return customers;
    }

    public ArrayList<Loan> getLoans() {
        @Nullable ArrayList<Loan> loans = new ArrayList<>();
        Request request = new Request.Builder()
                .url(Constants.GET_LOANS)
                .build();

        Call call = Constants.HTTP_CLIENT.newCall(request);
        try {
            Response response = call.execute();
            LoanList_json loanList_json;
            String resp = response.body().string();
            response.body().close();
            if(resp != null) {
                loanList_json = Constants.GSON_INSTANCE.fromJson(resp, LoanList_json.class);
                if(loanList_json.loans != null)
                    loans = loanList_json.loans;
            }
        } catch (IOException e) {
            System.out.println("Error when trying to get data. Exception: " + e.getMessage());
        }
        return loans;
    }

    public void updateBankDtos() {
        String finalUrl = HttpUrl
                .parse(Constants.UPDATE_BANK_DTOS)
                .newBuilder()
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
                } else {
                }
            }
        });
    }

    public void updateUserName(String userName, String isAdmin) {
        String finalUrl = HttpUrl
                .parse(Constants.UPDATE_USER_NAME)
                .newBuilder()
                .addQueryParameter("userName", userName)
                .addQueryParameter("isAdmin", isAdmin)
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
                } else {
                    username = userName;
                    new Thread(()->{
                        while(true){
                            Platform.runLater(()-> {
                                adminComponentController.showAdminScreen();
                            });
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }).start();
            }
        }
        });
    }
}