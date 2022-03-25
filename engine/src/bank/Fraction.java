package bank;
import bank.Customer;
public class Fraction {
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
}

