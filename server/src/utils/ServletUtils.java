package utils;

import bank.ChatManager;
import bank.UserManager;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import static constants.Constants.INT_PARAMETER_ERROR;

public class ServletUtils {
    private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";
    private static final String CHAT_MANAGER_ATTRIBUTE_NAME = "chatManager";
    private static final Object userManagerLock = new Object();
    private static final Object chatManagerLock = new Object();
    public static UserManager getUserManager(ServletContext servletContext) {
        synchronized (userManagerLock) {
            if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, new UserManager());
            }
        }
        return (UserManager) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
    }
}