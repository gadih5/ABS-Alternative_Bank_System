package bank;

import java.io.Serializable;

public class Debt implements Comparable<Debt>, Serializable {
    private Customer toCustomer;
    private double fundPart;
    private double interestPart;
    private double amount;

    public Debt(Customer toCustomer, double fundPart,double interestPart) {
        this.toCustomer = toCustomer;
        this.fundPart = fundPart;
        this.interestPart = interestPart;
        this.amount = fundPart + interestPart;
    }

    public Customer getToCustomer() {
        return toCustomer;
    }

    public double getFundPart() {
        return fundPart;
    }

    public double getInterestPart() {
        return interestPart;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public int compareTo(Debt d) {
        int res = 0;
        if (this.amount > d.getAmount()) {
            res = 1;
        } else if (this.amount < d.getAmount())
            res = -1;

        return res;
    }

    @Override
    public String toString() {
        return "Debt{" +
                "to customer:" + toCustomer.getName() +
                ", amount:" + amount +
                '}';
    }
}