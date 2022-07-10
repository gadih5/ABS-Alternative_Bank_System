package servlets;

import bank.Bank;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="GetCategories" ,urlPatterns="/getCategories")
public class GetCategories extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank)getServletContext().getAttribute("myBank");
        Gson gson = new Gson();
        String categories = gson.toJson(myBank.getCategory());

        /*String categories = "";
        for (String category : myBank.getCategory()) {
            categories += " ";
            categories += category;
        }*/
        resp.getWriter().println(categories);
    }
}
