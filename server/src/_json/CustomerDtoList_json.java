package _json;

import bank.CustomerDto;
import java.util.ArrayList;

public class CustomerDtoList_json {
    public ArrayList<CustomerDto> customersDtos;

    public CustomerDtoList_json(ArrayList<CustomerDto> customersDtos) {
        this.customersDtos = customersDtos;
    }
}