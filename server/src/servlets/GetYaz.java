package servlets;

import bank.Bank;
import bank.UserManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name="GetYaz" ,urlPatterns="/getYaz")
public class GetYaz extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank) getServletContext().getAttribute("myBank");
        resp.getWriter().println(myBank.getSyncGlobalTimeUnit());
        UserManager userManager = ServletUtils.getUserManager(getServletContext());

        for( HttpServletResponse hsr :userManager.responses){
            System.out.println(hsr);
            hsr.setStatus(10);
        }
    }
}
