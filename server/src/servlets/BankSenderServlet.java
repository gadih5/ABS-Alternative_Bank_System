package servlets;

import bank.Bank;
import bank.UserManager;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static utils.ServletUtils.getUserManager;

public class BankSenderServlet extends HttpServlet{

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            //returning JSON objects, not HTML
            response.setContentType("application/json");
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                Bank bank= (Bank)getServletContext().getAttribute("myBank");

                String json = gson.toJson(bank);
                out.println(json);
                out.flush();
            }
        }

    }


