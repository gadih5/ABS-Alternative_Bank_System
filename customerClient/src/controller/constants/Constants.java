package controller.constants;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;

public class Constants {
    public final static String BASE_DOMAIN = "localhost";
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/ABS";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;
    public final static String LOGIN_PAGE = FULL_SERVER_PATH + "/login";
    public final static String GET_LOANS = FULL_SERVER_PATH + "/getLoans";
    public final static String GET_CUSTOMERS = FULL_SERVER_PATH + "/getCustomers";
    public final static String GET_CUSTOMERS_DTO = FULL_SERVER_PATH + "/getCustomersDto";
    public final static String GET_LOANS_DTO = FULL_SERVER_PATH + "/getLoansDto";
    public final static String GET_CATEGORIES = FULL_SERVER_PATH + "/getCategories";
    public final static String GET_NUM_OF_LOANS = FULL_SERVER_PATH + "/getNumOfLoans";
    public final static String UPDATE_BANK_DTOS = FULL_SERVER_PATH + "/updateBankDtos";
    public final static String UPDATE_USER_NAME = FULL_SERVER_PATH + "/updateUserName";
    public final static String LOAD_XML = FULL_SERVER_PATH + "/loadXml";
    public final static String GET_YAZ = FULL_SERVER_PATH + "/getYaz";
    public final static String SELF_TRANSACTION = FULL_SERVER_PATH + "/selfTransaction";
    public final static String BANK_SENDER = FULL_SERVER_PATH + "/bankSenderServlet";
    public final static String CLEAR_DEBTS = FULL_SERVER_PATH + "/clearDebts";
    public final static String SET_FINISHED = FULL_SERVER_PATH + "/setFinished";
    public final static String GET_TRANS = FULL_SERVER_PATH + "/getTransactions";
    public final static String GET_NUM_OF_TRANS = FULL_SERVER_PATH + "/getNumOfTrans";
    public final static String GET_NEW_BALANCE = FULL_SERVER_PATH + "/getNewBalance";
    public final static String ADD_LOANER = FULL_SERVER_PATH + "/addLoaner";
    public final static String MAKE_TRANS = FULL_SERVER_PATH + "/makeTrans";
    public final static String ADD_TRANS = FULL_SERVER_PATH + "/addTrans";
    public final static String MARK_AS_PAID = FULL_SERVER_PATH + "/markAsPaid";
    public final static String GET_FRACTION = FULL_SERVER_PATH + "/getFraction";

    public final static Gson GSON_INSTANCE = new Gson();
    public final static OkHttpClient HTTP_CLIENT = new OkHttpClient();
}