package servlets;

import bank.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name="MarkAsPaid" ,urlPatterns="/markAsPaid")
public class MarkAsPaid extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank) getServletContext().getAttribute("myBank");
        String data = req.getHeader("data");
        List<String> dataList = Arrays.asList(data.split(","));
        String customerName = dataList.get(0);
        String loanName = dataList.get(1);
        for (Customer customer : Bank.getCustomers()) {
            if (customer.getName().equals(customerName)) {
                for(LoanDto loandto:myBank.getLoansDto()){
                    if(loandto.getLoanName().equals(loanName))
                        customer.makeAllPreTransactionsPaid(loandto);
                }
            }
        }
    }
}