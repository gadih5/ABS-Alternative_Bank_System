package bank;
import bank.Customer;

import java.io.Serializable;

public class Fraction implements Serializable {
    private Customer customer;
    private double amount;

    public Fraction(Customer customer, double amount) {
        this.customer = customer;
        this.amount = amount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getCustomerName() {
        return customer.getName();
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return
                "{"+getCustomerName() +
                 " , "+ amount +
                "} " ;
    }
}

