package controller.constants;

import com.google.gson.Gson;

public class Constants {

    // global constants
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    public final static String JHON_DOE = "<Anonymous>";
    public final static int REFRESH_RATE = 2000;
    public final static String CHAT_LINE_FORMATTING = "%tH:%tM:%tS | %.10s: %s%n";

    // fxml locations
   /* public final static String MAIN_PAGE_FXML_RESOURCE_LOCATION = "/chat/client/component/main/chat-app-main.fxml";
    public final static String LOGIN_PAGE_FXML_RESOURCE_LOCATION = "/chat/client/component/login/adminLogin.fxml";
    public final static String CHAT_ROOM_FXML_RESOURCE_LOCATION = "/chat/client/component/chatroom/chat-room-main.fxml";*/

    // Server resources locations
    public final static String BASE_DOMAIN = "localhost";
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/ABS";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;

    public final static String LOGIN_PAGE = FULL_SERVER_PATH + "/login";
    public final static String UPDATE_YAZ_PAGE = FULL_SERVER_PATH + "/updateYaz";
    public final static String GET_CUSTOMERS_NAMES = FULL_SERVER_PATH + "/getCustomersNames";
    public final static String GET_LOANS = FULL_SERVER_PATH + "/getLoans";
    public final static String CHECK_RISK_STATUS = FULL_SERVER_PATH + "/checkRiskStatus";
    public final static String GET_CUSTOMERS = FULL_SERVER_PATH + "/getCustomers";
    public final static String GET_CUSTOMERS_DTO = FULL_SERVER_PATH + "/getCustomersDto";
    public final static String GET_LOANS_DTO = FULL_SERVER_PATH + "/getLoansDto";
    public final static String GET_CATEGORIES = FULL_SERVER_PATH + "/getCategories";
    public final static String GET_NUM_OF_LOANS = FULL_SERVER_PATH + "/getNumOfLoans";
    public final static String UPDATE_BANK_DTOS = FULL_SERVER_PATH + "/updateBankDtos";
    public final static String UPDATE_USER_NAME = FULL_SERVER_PATH + "/updateUserName";
    public final static String IS_ADMIN = FULL_SERVER_PATH + "/isAdmin";
    public final static String ADMIN_LOGIN_PAGE = FULL_SERVER_PATH + "/adminLoginPage";










    public final static String USERS_LIST = FULL_SERVER_PATH + "/userslist";
    public final static String LOGOUT = FULL_SERVER_PATH + "/chat/logout";
    public final static String SEND_CHAT_LINE = FULL_SERVER_PATH + "/pages/chatroom/sendChat";
    public final static String CHAT_LINES_LIST = FULL_SERVER_PATH + "/chat";

    // GSON instance
    public final static Gson GSON_INSTANCE = new Gson();
}
