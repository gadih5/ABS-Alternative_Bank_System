package servlets;

import bank.Bank;
import bank.Customer;
import bank.PreTransaction;
import bank.Transaction;
import bank.exception.NegativeBalanceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
@WebServlet(name="AddTrans" ,urlPatterns="/addTrans")
public class AddTrans extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank) getServletContext().getAttribute("myBank");
        String data = req.getHeader("data");
        List<String> dataList = Arrays.asList(data.split(","));
        String fromCustomerName = dataList.get(0);
        String toCustomerName = dataList.get(1);
        String sum= dataList.get(2);
        System.out.println(fromCustomerName+" "+toCustomerName+" "+sum);
        double Dsum= Double.parseDouble(sum);
        Customer CfromCustomer=null;
        Customer CtoCustomer=null;
        for(Customer c :Bank.getCustomers()){
            if(c.getName().equals(fromCustomerName)){
                CfromCustomer=c;
            }
            if(c.getName().equals(toCustomerName)){
                CtoCustomer=c;
            }
        }
        try {
            CfromCustomer.addTransaction(new Transaction(CfromCustomer,CtoCustomer,Dsum));
        } catch (NegativeBalanceException e) {
            resp.setStatus(419);
        }

    }
}

