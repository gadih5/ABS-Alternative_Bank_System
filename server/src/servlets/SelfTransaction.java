package servlets;

import bank.Bank;
import bank.Customer;
import bank.exception.NegativeBalanceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="SelfTransaction" ,urlPatterns="/selfTransaction")
public class SelfTransaction extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank) getServletContext().getAttribute("myBank");
        for (Customer customer : myBank.getCustomers()) {
            if (customer.getName().equals(req.getParameter("username"))) {
                try {
                    customer.selfTransaction(Integer.parseInt(req.getParameter("amount")));
                } catch (NegativeBalanceException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
