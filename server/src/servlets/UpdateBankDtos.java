package servlets;

import bank.Bank;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="UpdateBankDtos" ,urlPatterns="/updateBankDtos")
public class UpdateBankDtos extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank)getServletContext().getAttribute("myBank");
        myBank.updateAllDtos();
    }
}