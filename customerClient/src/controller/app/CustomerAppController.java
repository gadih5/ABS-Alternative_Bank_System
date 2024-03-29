package controller.app;

import _json.*;
import bank.*;
import bank.exception.*;
import bank.xml.XmlReader;
import bank.xml.generated.AbsDescriptor;
import com.google.gson.JsonSyntaxException;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.HttpClientUtil;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

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

    private String username;

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

    public void setScrambleController(ScrambleController scrambleController) {
        this.scrambleController = scrambleController;
    }

    public void setPaymentController(PaymentController paymentController) {
        this.paymentController = paymentController;
    }

    public void changeBody(String userName) {
        try {
            URL url = getClass().getResource("/controller/customer/customer.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(url);
            customerComponent = fxmlLoader.load(url.openStream());
            customerComponentController = fxmlLoader.getController();
            setCustomerComponentController(customerComponentController);
            bodyAnchorPane.getChildren().set(0,customerComponent);
            AnchorPane.setBottomAnchor(customerComponent, 0.0);
            AnchorPane.setLeftAnchor(customerComponent, 0.0);
            AnchorPane.setRightAnchor(customerComponent, 0.0);
            AnchorPane.setTopAnchor(customerComponent, 0.0);
            customerComponentController.loadTabs();
            customerComponentController.loadCustomerDetails(userName, false);
            getYazValueFromBank();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getYazValueFromBank() {
        int yaz = 0;
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

    public void loadXmlData(AbsDescriptor descriptor) {
        try {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.LOAD_XML).newBuilder();
            urlBuilder.addQueryParameter("username", username);
            urlBuilder.addQueryParameter("descriptor", Constants.GSON_INSTANCE.toJson(descriptor));
            String finalUrl = urlBuilder.build().toString();
            Request request = new Request.Builder()
                    .url(finalUrl)
                    .build();

            Call call = Constants.HTTP_CLIENT.newCall(request);
            try {
                Response response = call.execute();
                response.body().close();
                if(response.code() == 406){
                    showErrorAlert("XML File Not Allowed");
                }
                else if(response.code() == 409){
                    showErrorAlert("Loan's Name Already Exist In The System");
                }
            } catch (IOException e) {
                System.out.println("Error when trying to get data. Exception: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        changeBody(username);
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error: " + message);
        alert.setContentText("Cant load this file, please try another file.");
        alert.showAndWait();
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

    public ArrayList<Customer> getCustomers() throws NullPointerException {
        ArrayList<Customer> customers = new ArrayList<>();
        Request request = new Request.Builder()
                .url(Constants.GET_CUSTOMERS)
                .build();

        Call call = Constants.HTTP_CLIENT.newCall(request);
        try {
            Response response = call.execute();
            CustomerList_json customerList_json;
            String resp = response.body().string();
            response.body().close();
            customerList_json = Constants.GSON_INSTANCE.fromJson(resp, CustomerList_json.class);
            customers = customerList_json.customers;
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
            if (resp != null) {
                loanList_json = Constants.GSON_INSTANCE.fromJson(resp, LoanList_json.class);
                if (loanList_json.loans != null)
                    loans = loanList_json.loans;
            }
        } catch (IOException e) {
            System.out.println("Error when trying to get data. Exception: " + e.getMessage());
        }
        return loans;
    }

    public Set<String> getCategories() {
        Set<String> categoriesSet = new HashSet<>();
        Request request = new Request.Builder()
                .url(Constants.GET_CATEGORIES)
                .build();

        Call call = Constants.HTTP_CLIENT.newCall(request);
        try {
            Response response = call.execute();
            CategoryList_json categoryList_json;
            String resp = response.body().string();
            response.body().close();
            if (resp != null) {
                categoryList_json = Constants.GSON_INSTANCE.fromJson(resp, CategoryList_json.class);
                if (categoryList_json != null)
                    categoriesSet = categoryList_json.categories;
            }
        } catch (IOException e) {
            System.out.println("Error when trying to get data. Exception: " + e.getMessage());
        }
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
        int numOfLoans = 0;
        Request request = new Request.Builder()
                .url(Constants.GET_NUM_OF_LOANS)
                .build();

        Call call = Constants.HTTP_CLIENT.newCall(request);
        try {
            Response response = call.execute();
            String resp = response.body().string();
            response.body().close();
            numOfLoans = Constants.GSON_INSTANCE.fromJson(resp, Integer.class);
        } catch (IOException e) {
            System.out.println("Error when trying to get data. Exception: " + e.getMessage());
        }
        return numOfLoans;
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
                response.body().close();
                if (response.code() != 200) {
                } else {
                }
            }
        });
    }

    public Loan getSpecificLoan(String loanName) {
        Loan resLoan = null;
        for (Loan loan : this.getLoans()) {
            if (loan.getLoanName().equals(loanName)) {
                resLoan = loan;
            }
        }
        return resLoan;
    }

    public Customer getSpecificCustomer(String name) {
        Customer resCustomer = null;
        for (Customer customer : this.getCustomers()) {
            if (customer.getName().equals(name)) {
                resCustomer = customer;
            }
        }
        return resCustomer;
    }

    public CustomerDto getSpecificCustomerDto(String name) {
        CustomerDto resCustomerDto = null;
        for (CustomerDto customerDto : this.getCustomersDto()) {
            if (customerDto.getName().equals(name)) {
                resCustomerDto = customerDto;
            }
        }
        return resCustomerDto;
    }

    public void checkLoanStatus(Loan loanToCheck) {
        if (loanToCheck.getStatus() != Status.Finished)
            loanToCheck.checkRiskStatus(this.getCustomers());
    }

    public void updateUserName(String _userName, String isAdmin) {
        this.username = _userName;
        String finalUrl = HttpUrl
                .parse(Constants.UPDATE_USER_NAME)
                .newBuilder()
                .addQueryParameter("userName", username)
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
                    new Thread(()-> {
                        while(true) {
                            String prevYaz = headerComponentController.getYazFromLabel();
                            updateBankDtos();
                            getYazValueFromBank();
                            customerComponentController.refreshInfo(getSpecificCustomerDto(username));
                            if(!prevYaz.equals(headerComponentController.getYazFromLabel()))
                                customerComponentController.showPaymentInfo(getSpecificCustomerDto(username));
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

    public void onClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
        File file = fileChooser.showOpenDialog(new Stage());
        try {
            String filePath = file.getPath();
            if (filePath != null) {
                try {
                    XmlReader myXml = new XmlReader(Paths.get(filePath));
                    loadXmlData(myXml.getDescriptor());
                } catch (JAXBException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (NotXmlException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    return;
                }
            } else
                return;
        } catch (Exception e) {
            return;
        }
    }

    public void setName(String name) {
        headerComponentController.setName(name);
    }

    public void selfTransaction(String name, int amount) {
        String finalUrl = HttpUrl
                .parse(Constants.SELF_TRANSACTION)
                .newBuilder()
                .addQueryParameter("username", name)
                .addQueryParameter("amount", String.valueOf(amount))
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

    public void clearAllDebts(LoanDto selectedLoan) {
        String finalUrl = HttpUrl
                .parse(Constants.CLEAR_DEBTS)
                .newBuilder()
                .addQueryParameter("selectedLoan", selectedLoan.getLoanName())
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

    public void setStatusFinished(LoanDto selectedLoan) {
        String finalUrl = HttpUrl
                .parse(Constants.SET_FINISHED)
                .newBuilder()
                .addQueryParameter("selectedLoan", selectedLoan.getLoanName())
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

    public ArrayList<Transaction> getTransactions(String name) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        Request request = new Request.Builder()
                .url(Constants.GET_TRANS)
                .addHeader("username", name)
                .build();

        Call call = Constants.HTTP_CLIENT.newCall(request);
        try {
            Response response = call.execute();
            String resp = response.body().string();
            response.body().close();
            TransactionList_json transactionList_json;
            transactionList_json = Constants.GSON_INSTANCE.fromJson(resp, TransactionList_json.class);
            if(transactionList_json.transactions != null)
                transactions = transactionList_json.transactions;
        } catch (IOException e) {
            System.out.println("Error when trying to get data. Exception: " + e.getMessage());
        }catch (JsonSyntaxException e){
        }
        return transactions;
    }

    public int getNumOfTrans(String name) {
        int numOfTrans = 0;
        Request request = new Request.Builder()
                .url(Constants.GET_NUM_OF_TRANS)
                .addHeader("username", name)
                .build();

        Call call = Constants.HTTP_CLIENT.newCall(request);
        try {
            Response response = call.execute();
            String resp = response.body().string();
            response.body().close();
            if(response.code() == 200) {
                numOfTrans = Constants.GSON_INSTANCE.fromJson(resp, Integer.class);
            }
        } catch (IOException e) {
            System.out.println("Error when trying to get data. Exception: " + e.getMessage());
        }
        return numOfTrans;
    }

    public double getNewBalance(String name) {
        double newBalance = 0;
        Request request = new Request.Builder()
                .url(Constants.GET_NEW_BALANCE)
                .addHeader("username", name)
                .build();

        Call call = Constants.HTTP_CLIENT.newCall(request);
        try {
            Response response = call.execute();
            String resp = response.body().string();
            response.body().close();
            if(response.code() == 200) {
                newBalance = Constants.GSON_INSTANCE.fromJson(resp, Double.class);
            }
        } catch (IOException e) {
            System.out.println("Error when trying to get data. Exception: " + e.getMessage());
        }
        return newBalance;
    }

    public void addLoaner(String loanName, String name, double sumToinvest) {
        String s = loanName+ "," + name + "," + sumToinvest;
        Request request = new Request.Builder()
                .url(Constants.ADD_LOANER)
                .addHeader("data", s)
                .build();

        Call call = Constants.HTTP_CLIENT.newCall(request);
        try {
            Response response = call.execute();
            response.body().close();
            if(response.code() == 200) {
            }
        } catch (IOException e) {
            System.out.println("Error when trying to get data. Exception: " + e.getMessage());
        }
    }

    public boolean makeTransaction(String id, String name) throws NegativeBalanceException{
        String s = id+ "," + name;
        Request request = new Request.Builder()
                .url(Constants.MAKE_TRANS)
                .addHeader("data", s)
                .build();

        Call call = Constants.HTTP_CLIENT.newCall(request);
        try {
            Response response = call.execute();
            response.body().close();
            if(response.code() == 200) {
                return true;
            }
            else{
                throw  new NegativeBalanceException("");
            }
        } catch (IOException e) {
            System.out.println("Error when trying to get data. Exception: " + e.getMessage());
        }
        return false;
    }

    public void addTransition(String name, String name1, String valueOf) throws NegativeBalanceException {
        String s = name+ "," + name1+","+valueOf;
        Request request = new Request.Builder()
                .url(Constants.ADD_TRANS)
                .addHeader("data", s)
                .build();

        Call call = Constants.HTTP_CLIENT.newCall(request);
        try {
            Response response = call.execute();
            response.body().close();
            if(response.code() == 200) {
            }
            else if(response.code()==419) {

                throw  new NegativeBalanceException("");
            }
        } catch (IOException e) {
            System.out.println("Error when trying to get data. Exception: " + e.getMessage());
        }
    }

    public void makeAllPreTransactionsPaid(String name, String selectedLoan) {
        String s = name+ "," + selectedLoan;
        Request request = new Request.Builder()
                .url(Constants.MARK_AS_PAID)
                .addHeader("data", s)
                .build();

        Call call = Constants.HTTP_CLIENT.newCall(request);
        try {
            Response response = call.execute();
            response.body().close();
            if(response.code() == 200) {
            }
            else if(response.code()==419) {
            }
        } catch (IOException e) {
            System.out.println("Error when trying to get data. Exception: " + e.getMessage());
        }
    }

    public  Collection<Fraction> getFractions(String loanName) {
        Collection<Fraction> fractions = new ArrayList<>();
        Request request = new Request.Builder()
                .url(Constants.GET_FRACTION)
                .addHeader("loanName", loanName)
                .build();

        Call call = Constants.HTTP_CLIENT.newCall(request);
        try {
            Response response = call.execute();
            String resp = response.body().string();
            response.body().close();
            FractionList_json fractionList_json;
            fractionList_json = Constants.GSON_INSTANCE.fromJson(resp, FractionList_json.class);
            fractions= fractionList_json.fractions;
        } catch (IOException e) {
            System.out.println("Error when trying to get data. Exception: " + e.getMessage());
        }catch (JsonSyntaxException e){
        }
        return fractions;
    }
}