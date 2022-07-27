package servlets;

import bank.Bank;
import bank.exception.*;
import bank.xml.generated.AbsDescriptor;
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
      Gson gson = new Gson();
        Bank myBank = (Bank)getServletContext().getAttribute("myBank");
        AbsDescriptor descriptor = gson.fromJson(req.getParameter("descriptor"),AbsDescriptor.class);
        try {
            myBank.loadXmlData(req.getParameter("username"),descriptor);
        } catch (NegativeInterestPercentException e) {
            resp.setStatus(406);
        }catch (UndefinedReasonException e){
            resp.setStatus(409);
        } catch (NegativeLoanSumException e) {
            resp.setStatus(406);
        } catch (NegativeTotalTimeUnitException e) {
            resp.setStatus(406);
        } catch (NegativePaymentFrequencyException e) {
            resp.setStatus(406);
        } catch (OverPaymentFrequencyException e) {
            resp.setStatus(406);
        } catch (UndividedPaymentFrequencyException e) {
            resp.setStatus(406);
        } catch (NotInCategoryException e) {
            resp.setStatus(406);
        }
    }
}