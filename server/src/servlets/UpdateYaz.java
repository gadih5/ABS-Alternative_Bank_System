package servlets;

import bank.Bank;
import bank.exception.NegativeBalanceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="UpdateYaz" ,urlPatterns="/updateYaz")
public class UpdateYaz extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Bank myBank = (Bank)getServletContext().getAttribute("myBank");
            myBank.updateGlobalTimeUnit();
            resp.getWriter().println(myBank.getSyncGlobalTimeUnit());
        } catch (NegativeBalanceException e) {
            throw new RuntimeException(e);
        }
    }
}