package servlets;

import bank.Bank;
import bank.Loan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="ClearDebts" ,urlPatterns="/clearDebts")
public class ClearDebts extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank)getServletContext().getAttribute("myBank");
        for(Loan loan: myBank.getLoans()){
            if(loan.getLoanName().equals(req.getParameter("selectedLoan"))){
                loan.clearAllDebts();
                break;
            }
        }
    }
}