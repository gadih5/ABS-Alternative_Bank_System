package servlets;

import _json.FractionList_json;
import _json.TransactionList_json;
import bank.*;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kotlin.collections.UCollectionsKt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet(name="GetFraction" ,urlPatterns="/getFraction")
public class GetFraction extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank) getServletContext().getAttribute("myBank");
        Loan Loan = null;
        for(Loan loan: Bank.getLoans()){
            if(loan.getLoanName().equals(req.getHeader("loanName"))){
                Loan=loan;
                break;
            }
        }
        if(Loan != null) {
            Gson gson = new Gson();
            Collection<Fraction> fractions= Loan.getFractions();
            FractionList_json fractionList_json = new FractionList_json(fractions);
            resp.getWriter().write(gson.toJson(fractionList_json));
                    }
    }

}
