package servlets;

import bank.Bank;
import bank.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

@WebServlet(name="IsAdmin" ,urlPatterns="/isAdmin")
public class IsAdmin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank) getServletContext().getAttribute("myBank");
        ArrayList<Customer> customers = myBank.getCustomers();
        for(Customer customer: customers){
            if(customer.getName().equals(req.getParameter("userName"))){
                resp.getWriter().println(customer.getAdmin());
            }
        }
    }
}
