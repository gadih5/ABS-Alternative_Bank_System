package bank;

public class Debt {
    private Customer toCustomer;
    private double amount;

    public Debt(Customer toCustomer, double amount) {
        this.toCustomer = toCustomer;
        this.amount = amount;
    }

    public Customer getToCustomer() {
        return toCustomer;
    }

    public double getAmount() {
        return amount;
    }
}
