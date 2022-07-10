package servlets;

import bank.Bank;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

@WebServlet(name="GetCustomersDto" ,urlPatterns="/getCustomersDto")
public class GetCustomersDto extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank)getServletContext().getAttribute("myBank");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        try {
            oos.writeObject(myBank.getCustomersDto());
        } finally {
            oos.close();
        }
        byte[] bytes = baos.toByteArray();
        resp.getOutputStream().write(bytes);
    }
}
