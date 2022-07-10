package servlets;

import bank.Bank;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import javax.servlet.annotation.WebListener;

@WebListener
@WebServlet(name="ServerBank" ,urlPatterns="/serverBank")
public class ServerBank extends HttpServlet implements ServletContextListener {
    //private Bank myBank;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Bank myBank = new Bank();
        sce.getServletContext().setAttribute("myBank",myBank);
        System.out.println("BANK CREATED");
        //System.out.println("YAZ: " + myBank.getSyncGlobalTimeUnit());
    }
}
