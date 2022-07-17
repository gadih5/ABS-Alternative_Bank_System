package servlets;

import bank.Bank;
import bank.Customer;
import com.google.gson.Gson;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import javax.servlet.annotation.WebListener;
import java.util.ArrayList;

@WebListener
@WebServlet(name="ServerBank" ,urlPatterns="/serverBank")
public class ServerBank extends HttpServlet implements ServletContextListener {
    //private Bank myBank;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Bank myBank = new Bank();
        ArrayList<String> ServerCustomers= new ArrayList<>();
        sce.getServletContext().setAttribute("myBank",myBank);
        sce.getServletContext().setAttribute("Customers",ServerCustomers);
        System.out.println("BANK CREATED");


        /*Gson gson = new Gson();
        sce.getServletContext().setAttribute("myGson",gson);
        System.out.println("GSON CRATED");*/
        //System.out.println("YAZ: " + myBank.getSyncGlobalTimeUnit());
    }
}
