package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="CheckRiskStatus" ,urlPatterns="/checkRiskStatus")
public class CheckRiskStatus extends HttpServlet {
    private ServerBank serverBank;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serverBank.myBank.checkRiskStatus();
    }
}
