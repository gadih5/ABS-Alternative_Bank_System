package servlets;


import java.nio.charset.StandardCharsets;
import _json.CategoryList_json;
import bank.Bank;
import bank.exception.*;
import bank.xml.generated.AbsDescriptor;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@WebServlet(name="LoadXml" ,urlPatterns="/loadXml")
public class LoadXml extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      Gson gson = new Gson();
        Bank myBank = (Bank)getServletContext().getAttribute("myBank");
        System.out.println("-----------------------------------");
        //System.out.println(req.getInputStream().toString());
        System.out.println("-----------------------------------");
        /*StringBuilder fileContent = new StringBuilder();
        fileContent.append(new Scanner(req.getInputStream()));
        String desctriptor_string = new String(fileContent);*/
        AbsDescriptor descriptor = gson.fromJson(req.getParameter("descriptor"),AbsDescriptor.class);
        try {
            myBank.loadXmlData(req.getParameter("username"),descriptor);
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
