package servlets;

import bank.exception.NegativeBalanceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="UpdateYaz" ,urlPatterns="/updateYaz")
public class UpdateYaz extends HttpServlet {
    private ServerBank serverBank;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            serverBank.myBank.updateGlobalTimeUnit();
            resp.getWriter().println(serverBank.myBank.getSyncGlobalTimeUnit());
        } catch (NegativeBalanceException e) {
            throw new RuntimeException(e);
        }
    }
}
