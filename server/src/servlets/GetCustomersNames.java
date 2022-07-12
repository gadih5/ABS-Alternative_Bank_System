package servlets;

import bank.Bank;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="GetCustomersNames" ,urlPatterns="/getCustomersNames")
public class GetCustomersNames extends HttpServlet {

   /* @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank)getServletContext().getAttribute("myBank");
        Gson gson = new Gson();
        String names = gson.toJson(myBank.getCustomersNames());
        resp.getWriter().println(names);
    }*/
}
