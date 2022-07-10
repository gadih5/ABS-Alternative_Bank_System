package servlets;

import bank.Bank;
import bank.exception.NegativeBalanceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="UpdateUserName" ,urlPatterns="/updateUserName")
public class UpdateUserName extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank)getServletContext().getAttribute("myBank");
        try {
            myBank.addCustumer(req.getParameter("userName"),0, req.getParameter("isAdmin"));
        } catch (NegativeBalanceException e) {
            throw new RuntimeException(e);//never thrown
        }
    }
}
