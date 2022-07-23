package servlets;

import bank.Bank;
import bank.Customer;
import bank.Loan;
import bank.exception.NegativeBalanceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name="AddLoaner" ,urlPatterns="/addLoaner")
public class AddLoaner extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank) getServletContext().getAttribute("myBank");
        String data = req.getHeader("data");
        List<String> dataList = Arrays.asList(data.split(","));
        String loanname = dataList.get(0);
        String name = dataList.get(1);
        String sum = dataList.get(2);

        Loan loan = null;
        for (Loan loan1 : myBank.getLoans()) {
            if (loan1.getLoanName().equals(loanname)) {
                loan = loan1;
                break;
            }
        }

        Customer customer = null;
        for (Customer cust : myBank.getCustomers()) {
            if (cust.getName().equals(name)) {
                customer = cust;
                break;
            }
        }

        if (loan != null && customer != null) {
            try {
                loan.addLoaner(customer, Double.parseDouble(sum));
            } catch (NegativeBalanceException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
