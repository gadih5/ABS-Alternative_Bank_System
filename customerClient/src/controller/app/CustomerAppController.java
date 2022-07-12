package controller.app;

import _json.CustomerDtoList_json;
import _json.CustomerList_json;
import _json.LoanDtoList_json;
import _json.LoanList_json;
import bank.*;
import com.google.gson.Gson;
import com.google.gson.internal.bind.util.ISO8601Utils;
import controller.constants.Constants;
import controller.customer.CustomerController;
import controller.header.HeaderController;
import controller.payment.PaymentController;
import controller.scramble.ScrambleController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import utils.HttpClientUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomerAppController {
    @FXML
    private GridPane headerComponent;
    @FXML
    private HeaderController headerComponentController;
    @FXML
    private VBox adminComponent;
    @FXML
    private TabPane customerComponent;
    @FXML
    private CustomerController customerComponentController;
    @FXML
    private ScrambleController scrambleController;
    @FXML
    private PaymentController paymentController;
    @FXML
    private AnchorPane bodyAnchorPane;


    @FXML
    public void initialize() {
        if (headerComponentController != null) {
            headerComponentController.setMainController(this);
        }
    }

    public void setHeaderComponentController(HeaderController headerComponentController) {
        this.headerComponentController = headerComponentController;
        headerComponentController.setMainController(this);
    }


    public void setCustomerComponentController(CustomerController customerComponentController) {
        this.customerComponentController = customerComponentController;
        customerComponentController.setMainController(this);
    }

    public void updatePathLabel(String filePath) {
        headerComponentController.updatePathLabel(filePath);
    }

    public void setScrambleController(ScrambleController scrambleController) {
        this.scrambleController = scrambleController;

    }

    public void setPaymentController(PaymentController paymentController) {
        this.paymentController = paymentController;

    }

    /*public void updateYaz() {

        try {

            String finalUrl = HttpUrl
                    .parse(Constants.UPDATE_YAZ_PAGE)
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
                    if (response.code() != 200) {

                    } else {
                        headerComponentController.updateYazLabel(response.message());
                        adminComponentController.showAdminScreen();

                        String finalUrl = HttpUrl
                                .parse(Constants.CHECK_RISK_STATUS)
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
                                if (response.code() != 200) {

                                } else {

                                }
                            }
                        });
                    }
                }
            });


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }*/
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
                        if (response.code() != 200) {
                        } else {
                            try {
                                URL url = getClass().getResource("/controller/customer/customer.fxml");
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(url);
                                customerComponent = fxmlLoader.load(url.openStream());
                                customerComponentController = fxmlLoader.getController();
                                setCustomerComponentController(customerComponentController);
                                customerComponentController.loadCustomerDetails(userName, false);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
        });
    }

    /*public void addUsers() {
        String finalUrl = HttpUrl
                .parse(Constants.GET_CUSTOMERS)
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
                if (response.code() != 200) {

                } else {
                    Gson gson = new Gson();
                    //ArrayList<Customer> customers = new ArrayList<>();
                    //gson.fromJson(response.body().string() );

                    //headerComponentController.removeAllUsers();
                    //headerComponentController.addAdminBtn();
                   // for (int i=0 ; i<names.length ; i++) {
                      //  headerComponentController.addUserBtn(names[i]);
                    }
                }
          //  }
        });
    }*/

    /*public void setUserComboBoxEnable() {
        headerComponentController.setUserComboBoxEnable();
    }*/

    /*public boolean loadXmlData(AbsDescriptor descriptor) {
        boolean res = false;
        Bank newBank = new Bank();
        try {
            newBank.loadXmlData(descriptor);
            myBank = newBank;
            res = true;
        } catch (NegativeBalanceException e) {
            showErrorAlert("Negative Balance!");
        } catch (UndefinedReasonException e) {
            showErrorAlert("Undefined Reason!");
        } catch (UndefinedCustomerException e) {
            showErrorAlert("Undefined Customer!");
        } catch (NegativeLoanSumException e) {
            showErrorAlert("Negative Loan Sum!");
        } catch (NegativeTotalTimeUnitException e) {
            showErrorAlert("Negative Total YAZ!");
        } catch (NegativeInterestPercentException e) {
            showErrorAlert("Negative Interest Percent!");
        } catch (NegativePaymentFrequencyException e) {
            showErrorAlert("Negative Payment Frequency!");
        } catch (OverPaymentFrequencyException e) {
            showErrorAlert("Over Payment Frequency!");
        } catch (UndividedPaymentFrequencyException e) {
            showErrorAlert("Undivided Payment Frequency!");
        } catch (NotInCategoryException e) {
            showErrorAlert("Category is Missing!");
        } catch (AlreadyExistCustomerException e) {
            showErrorAlert("Already Exist Customer!");
        } finally {
            return res;
        }
    }*/

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error: " + message);
        alert.setContentText("Cant load this file, please try another file.");
        alert.showAndWait();
    }

    public Collection<LoanDto> getLoansDto() {
        final Collection<LoanDto>[] loansDto = new Collection[]{null};

        String finalUrl = HttpUrl
                .parse(Constants.GET_LOANS_DTO)
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
                String resp = response.body().string();
                response.body().close();
                if (response.code() != 200) {

                } else {
                    Gson gson = new Gson();
                    LoanDtoList_json loanDtoList_json = gson.fromJson(resp, LoanDtoList_json.class);
                    loansDto[0] = loanDtoList_json.loansDtos;
                }
            }

        });
        return loansDto[0];
    }
    public ArrayList<CustomerDto> getCustomersDto(){
        final ArrayList<CustomerDto>[] customerDtos = new ArrayList[]{new ArrayList<>()};
            String finalUrl = HttpUrl
                    .parse(Constants.GET_CUSTOMERS_DTO)
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
                    String resp = response.body().string();
                    response.body().close();
                    if (response.code() != 200) {
                    } else {
                        Gson gson = new Gson();
                        CustomerDtoList_json customerDtoList_json = gson.fromJson(resp, CustomerDtoList_json.class);
                        customerDtos[0] = customerDtoList_json.customersDtos;
                    }
                }
            });

        return customerDtos[0];
    }

    public Collection<Customer> getCustomers() {
        Collection<Customer>[] customers = new Collection[]{null};
        String finalUrl = HttpUrl
                .parse(Constants.GET_CUSTOMERS)
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
                String resp = response.body().string();
                response.body().close();
                if (response.code() != 200) {

                } else {
                    Gson gson = new Gson();
                    CustomerList_json customerList_json = gson.fromJson(resp, CustomerList_json.class);
                    customers[0] = customerList_json.customers;
                }

            }

        });
        return customers[0];
    }

    public Collection<Loan> getLoans() {
        Collection<Loan>[] loans = new Collection[]{null};
        String finalUrl = HttpUrl
                .parse(Constants.GET_LOANS)
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
                String resp = response.body().string();
                response.body().close();
                if (response.code() != 200) {

                } else {
                    Gson gson = new Gson();
                    LoanList_json loanList_json = gson.fromJson(resp, LoanList_json.class);
                    loans[0] = loanList_json.loans;
                }


            }

        });
        return loans[0];
    }

    public void initYazLabel() {
        headerComponentController.initYazLabel();
    }

  /*  public void eraseBank() {
        myBank = new Bank();
    }*/

    public Set<String> getCategories() {
        Set<String> categoriesSet = new HashSet<>();
        String finalUrl = HttpUrl
                .parse(Constants.GET_CATEGORIES)
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
                Gson gson = new Gson();
                String[] categories = gson.fromJson(response.message(),String[].class);

                for(String category: categories){
                    categoriesSet.add(category);
                }
            }
        });

        return categoriesSet;
    }

    public int calcMaxInterestPercent(CustomerDto selectedCustomer) {
        int res = 0;
        for (LoanDto loanDto : this.getLoansDto()) {
            if (loanDto.getInterestPercent() > res && !(loanDto.getBorrowerName().equals(selectedCustomer.getName())))
                res = loanDto.getInterestPercent();
        }
        return res;
    }

    public int calcMaxTotalYaz(CustomerDto selectedCustomer) {
        int res = 0;
        for (LoanDto loanDto : this.getLoansDto()) {
            if (loanDto.getTotalTimeUnit() > res && !(loanDto.getBorrowerName().equals(selectedCustomer.getName())))
                res = loanDto.getTotalTimeUnit();
        }
        return res;
    }

    public int getNumOfLoans() {
        final int[] numOfLoans = {0};
        String finalUrl = HttpUrl
                .parse(Constants.GET_NUM_OF_LOANS)
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
                if (response.code() != 200) {

                } else {
                    numOfLoans[0] = Integer.parseInt(response.message());
                }
            }

        });


        return numOfLoans[0];
    }

    public Collection<LoanDto> getLoansDtoForScramble(int categoriesChosed, Set<String> chosenCategories, int minInterestPercent, int minTotalYaz, int maxOpenLoans, int maxOwnershipPercent, CustomerDto selectedCustomer) {
        Set<LoanDto> validLoans = new HashSet<>();
        for (LoanDto loanDto : this.getLoansDto()) {
            if (loanDto.getStatus() == Status.Pending) {
                if (!(loanDto.getBorrowerName().equals(selectedCustomer.getName()))) {
                    if (chosenCategories.contains(loanDto.getReason()) || categoriesChosed == -1) {
                        if (loanDto.getInterestPercent() <= minInterestPercent || minInterestPercent == -1) {
                            if (loanDto.getTotalTimeUnit() <= minTotalYaz || minTotalYaz == -1) {
                                 if (maxOpenLoans == -1)
                                        validLoans.add(loanDto);
                                    else {
                                        Customer borrower = null;
                                        for (Customer customer : this.getCustomers()) {
                                            if (customer.getName().equals(loanDto.getBorrowerName())) {
                                                borrower = customer;
                                                break;
                                            }
                                        }
                                        if (borrower != null) {
                                            Set<LoanDto> loans = new HashSet<>();
                                            for (Loan loan : this.getLoans()) {
                                                if (loan.getStatus() != Status.Finished && loan.getBorrower().getName().equals(borrower.getName())) {
                                                    loans.add(loan.getLoanDto());
                                                }
                                            }
                                            if (loans.size() <= maxOpenLoans) {
                                                validLoans.add(loanDto);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return validLoans;
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
                if (response.code() != 200) {
                } else {
                }
            }
        });

    }

    public Loan getSpecificLoan(String loanName) {
        Loan resLoan = null;
        for(Loan loan : this.getLoans()){
            if(loan.getLoanName().equals(loanName)){
                resLoan = loan;
            }
        }
        return resLoan;
    }

    public Customer getSpecificCustomer(String name) {
        Customer resCustomer = null;
        for(Customer customer : this.getCustomers()){
            if(customer.getName().equals(name)){
                resCustomer = customer;
            }
        }
        return resCustomer;
    }

    public CustomerDto getSpecificCustomerDto(String name) {
        CustomerDto resCustomerDto = null;
        for(CustomerDto customerDto : this.getCustomersDto()){
            if(customerDto.getName().equals(name)){
                resCustomerDto = customerDto;
            }
        }
        return resCustomerDto;
    }

    public void checkLoanStatus(Loan loanToCheck) {
        if(loanToCheck.getStatus() != Status.Finished)
            loanToCheck.checkRiskStatus(this.getCustomers());
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
                if (response.code() != 200) {
                } else {
                    //addUsers();
                }
            }
        });
    }

    public void onClick(ActionEvent actionEvent) {
    }

    public void setName(String name) {
        headerComponentController.setName(name);
    }
}