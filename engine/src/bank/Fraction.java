package bank;

import java.io.Serializable;

public class Fraction implements Serializable {
    private int convertTime=0;
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

    public int getConvertTime() {
        return convertTime;
    }

    public void setConvertTime(int updateConvertTime) {
        this.convertTime = updateConvertTime;
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