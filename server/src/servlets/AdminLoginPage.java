package servlets;

import bank.Bank;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="AdminLoginPage" ,urlPatterns="/adminLoginPage")
public class AdminLoginPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank) getServletContext().getAttribute("myBank");
        Boolean adminLogged = myBank.adminLogged();
        if (adminLogged){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}