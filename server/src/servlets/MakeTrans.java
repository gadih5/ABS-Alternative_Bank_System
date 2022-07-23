package servlets;

import bank.Bank;
import bank.Customer;
import bank.PreTransaction;
import bank.exception.NegativeBalanceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name="MakeTrans" ,urlPatterns="/makeTrans")
public class MakeTrans extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank) getServletContext().getAttribute("myBank");
        String data = req.getHeader("data");
        List<String> dataList = Arrays.asList(data.split(","));
        String preTransId = dataList.get(0);
        String fromCustomerName = dataList.get(1);
        for (Customer customer : Bank.getCustomers()) {
            if (customer.getName().equals(fromCustomerName)) {
                            for(PreTransaction preTransaction: customer.getPreTransactions()){
                   if(preTransaction.getId().equals(preTransId)){
                       try {

                           preTransaction.makeTransaction(customer.getCustomerDto());

                           myBank.updateAllDtos();
                           break;
                       } catch (NegativeBalanceException e) {
                           resp.setStatus(419);
                       }
                   }

               }
            }

        }
    }
}
