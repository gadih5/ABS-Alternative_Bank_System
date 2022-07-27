package controller.constants;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;

public class Constants {
    public final static String BASE_DOMAIN = "localhost";
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/ABS";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;
    public final static String UPDATE_YAZ_PAGE = FULL_SERVER_PATH + "/updateYaz";
    public final static String GET_LOANS = FULL_SERVER_PATH + "/getLoans";
    public final static String GET_CUSTOMERS = FULL_SERVER_PATH + "/getCustomers";
    public final static String GET_CUSTOMERS_DTO = FULL_SERVER_PATH + "/getCustomersDto";
    public final static String GET_LOANS_DTO = FULL_SERVER_PATH + "/getLoansDto";
    public final static String UPDATE_BANK_DTOS = FULL_SERVER_PATH + "/updateBankDtos";
    public final static String UPDATE_USER_NAME = FULL_SERVER_PATH + "/updateUserName";
    public final static String IS_ADMIN = FULL_SERVER_PATH + "/isAdmin";
    public final static String ADMIN_LOGIN_PAGE = FULL_SERVER_PATH + "/adminLoginPage";
    public final static String GET_YAZ = FULL_SERVER_PATH + "/getYaz";

    public final static Gson GSON_INSTANCE = new Gson();
    public final static OkHttpClient HTTP_CLIENT = new OkHttpClient();
}