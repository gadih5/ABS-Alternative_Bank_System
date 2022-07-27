package _json;

import bank.Customer;
import java.util.ArrayList;

public class CustomerList_json {
    public ArrayList<Customer> customers;

    public CustomerList_json(ArrayList<Customer> customers) {
        this.customers = customers;
    }
}