package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="GetCustomersNames" ,urlPatterns="/getCustomersNames")
public class GetCustomersNames extends HttpServlet {
    private ServerBank serverBank;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String names = "";
        for (bank.Customer customer : serverBank.myBank.getCustomers()) {
            names += " ";
             names += customer.getName();
        }
        resp.getWriter().println(names);
    }
}
