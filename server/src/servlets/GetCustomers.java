package servlets;

import _json.CustomerDtoList_json;
import _json.CustomerList_json;
import _json.Customer_json;
import bank.Bank;
import bank.Customer;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

@WebServlet(name="GetCustomers" ,urlPatterns="/getCustomers")
public class GetCustomers extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank)getServletContext().getAttribute("myBank");
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(new CustomerList_json(myBank.getCustomers())));
    }
}
