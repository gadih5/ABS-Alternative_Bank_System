package _json;

import bank.Customer;
import bank.LoanDto;

import java.util.ArrayList;

public class LoanDtoList_json {
    public ArrayList<LoanDto> loansDtos;

    public LoanDtoList_json(ArrayList<LoanDto> loansDtos) {
        this.loansDtos = loansDtos;
    }
}
