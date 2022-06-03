package bank;
import bank.Customer;
import bank.exception.NegativeBalanceException;

import java.io.Serializable;

public class Transaction implements Serializable {
    private Customer toCustomer;
    private double amount; //positive value
    private int timeUnit;
    private double previousBalance;
    private double afterBalance;

    private String sign;

    public Transaction(Customer fromCustomer,Customer toCustomer, double amount) throws NegativeBalanceException {
        this(fromCustomer,toCustomer,amount,true);
    }
    public Transaction(Customer fromCustomer,Customer toCustomer, double amount, boolean firstTime) throws NegativeBalanceException {
        sign = firstTime ? "-" : "+";
        boolean amountChange = false;
        this.previousBalance = fromCustomer.getBalance();
        this.timeUnit = Bank.getGlobalTimeUnit();
        this.toCustomer = toCustomer;
        this.amount = amount; //check in ui that positive
        if (fromCustomer == toCustomer) {//its a self deposit
            sign=amount>0?"+":"-";
            afterBalance = previousBalance + amount;
            toCustomer.setBalance(afterBalance);
        }
        else {
            previousBalance = fromCustomer.getBalance();
            if (firstTime&&previousBalance - amount < 0)

                throw new NegativeBalanceException("Not enough money to complete this transaction");
            else {
                fromCustomer.setBalance(previousBalance - amount);
                afterBalance = previousBalance - amount;
                if(firstTime)
                toCustomer.addTransaction(new Transaction(toCustomer, fromCustomer, -amount, false));
            }


        }

    }
    public Customer getToCustomer() {
        return toCustomer;
    }

    public double getAmount() {
        double res = Math.abs(amount);
        return res;
    }

    public int getTimeUnit() {
        return timeUnit;
    }

    public double getPreviousBalance() {
        return previousBalance;
    }

    public double getAfterBalance() {
        return afterBalance;
    }

    public String getSign() {
        return sign;
    }
}
