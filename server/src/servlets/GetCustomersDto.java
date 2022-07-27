package servlets;

import _json.CustomerDtoList_json;
import bank.Bank;
import bank.CustomerDto;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import static java.lang.System.out;

@WebServlet(name="GetCustomersDto" ,urlPatterns="/getCustomersDto")
public class GetCustomersDto extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank)getServletContext().getAttribute("myBank");
        Gson gson = new Gson();
        ArrayList<CustomerDto> list  = new ArrayList<>();
        list = myBank.getCustomersDto();
        CustomerDtoList_json list_json = new CustomerDtoList_json(list);
        String s = gson.toJson(list_json);
        resp.getWriter().write(s);
        out.flush();
    }
}