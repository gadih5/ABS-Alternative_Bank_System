package bank;

public class Debt implements Comparable {
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

    @Override
    public int compareTo(Object o) {
        Debt d = (Debt) o;
        int res = 0;
        if (this.amount > d.getAmount()) {
            res = 1;
        } else if (this.amount < d.getAmount())
            res = -1;

        return res;
    }
}


