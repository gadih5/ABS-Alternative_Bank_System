package servlets;

import _json.TransactionList_json;
import bank.Bank;
import bank.Customer;
import bank.Transaction;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name="GetTransactions" ,urlPatterns="/getTransactions")
public class GetTransactions extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank)getServletContext().getAttribute("myBank");
        Customer customer = null;
        for(Customer cust: myBank.getCustomers()){
            if(cust.getName().equals(req.getHeader("username"))){
                customer = cust;
                break;
            }
        }
        if(customer != null) {
            Gson gson = new Gson();
            ArrayList<Transaction> transactions = customer.getTransactions();
            TransactionList_json transactionList_json = new TransactionList_json(transactions);
            resp.getWriter().write(gson.toJson(transactionList_json));
        }
    }
}