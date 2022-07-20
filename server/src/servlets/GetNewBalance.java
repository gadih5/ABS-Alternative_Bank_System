package servlets;

import bank.Bank;
import bank.Customer;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="GetNewBalance" ,urlPatterns="/getNewBalance")
public class GetNewBalance extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank) getServletContext().getAttribute("myBank");
        Customer customer = null;
        for(Customer cust: myBank.getCustomers()){
            if(cust.getName().equals(req.getHeader("username"))){
                customer = cust;
                break;
            }
        }
        if(customer != null) {
            Gson gson = new Gson();
            resp.getWriter().write(gson.toJson(customer.getBalance()));
        }
    }
}
