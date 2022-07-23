package servlets;

import _json.CustomerDtoList_json;
import _json.LoanDtoList_json;
import bank.Bank;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

@WebServlet(name="GetLoansDto" ,urlPatterns="/getLoansDto")
public class GetLoansDto extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank)getServletContext().getAttribute("myBank");
        Gson gson = new Gson();

        resp.getWriter().write(gson.toJson(new LoanDtoList_json(myBank.getLoansDto())));
    }
}
