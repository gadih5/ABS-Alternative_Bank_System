package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

@WebServlet(name="GetCustomers" ,urlPatterns="/getCustomers")
public class GetCustomers extends HttpServlet {
    private ServerBank serverBank;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        try {
            oos.writeObject(serverBank.myBank.getCustomers());
        } finally {
            oos.close();
        }
        byte[] bytes = baos.toByteArray();
        resp.getOutputStream().write(bytes);
    }
}
