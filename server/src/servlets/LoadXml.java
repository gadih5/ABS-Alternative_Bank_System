package servlets;

import _json.CategoryList_json;
import bank.Bank;
import bank.exception.*;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="LoadXml" ,urlPatterns="/loadXml")
public class LoadXml extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bank myBank = (Bank)getServletContext().getAttribute("myBank");
        try {
            myBank.loadXmlData(req.getParameter("username"));
        } catch (UndefinedReasonException e) {
            throw new RuntimeException(e);
        } catch (NegativeLoanSumException e) {
            throw new RuntimeException(e);
        } catch (NegativeTotalTimeUnitException e) {
            throw new RuntimeException(e);
        } catch (NegativeInterestPercentException e) {
            throw new RuntimeException(e);
        } catch (NegativePaymentFrequencyException e) {
            throw new RuntimeException(e);
        } catch (OverPaymentFrequencyException e) {
            throw new RuntimeException(e);
        } catch (UndividedPaymentFrequencyException e) {
            throw new RuntimeException(e);
        } catch (NotInCategoryException e) {
            throw new RuntimeException(e);
        }
    }
}
