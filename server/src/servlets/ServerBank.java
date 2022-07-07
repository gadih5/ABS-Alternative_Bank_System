package servlets;

import bank.Bank;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import javax.servlet.annotation.WebListener;

public class ServerBank implements ServletContextListener {
    public static Bank myBank;
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        myBank = new Bank();
        System.out.println("BANK CREATED");
        System.out.println("YAZ: " + myBank.getSyncGlobalTimeUnit());
    }
}
