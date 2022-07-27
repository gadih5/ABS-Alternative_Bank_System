package _json;

import bank.Loan;

import java.util.ArrayList;

public class LoanList_json {
    public ArrayList<Loan> loans;
    public LoanList_json(ArrayList<Loan> loans) {
        this.loans = loans;
    }
}