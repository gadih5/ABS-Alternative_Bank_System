package _json;

import bank.Customer;
import bank.exception.NegativeBalanceException;

import java.util.ArrayList;

public class CustomerList_json {
    public ArrayList<Customer> customers;

    public CustomerList_json(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public CustomerList_json() {
        ArrayList<Customer> init = new ArrayList<>();
        try {
            init.add(new Customer("default", 0, false));
        } catch (NegativeBalanceException e) {
            throw new RuntimeException(e);
        }
        customers = init;
    }
}

