package bank;
import bank.Customer;
import bank.exception.NegativeBalanceException;

public class Transaction {
    private Customer toCustomer;
    private double amount; //positive value
    private final int timeUnit;
    private double previousBalance;
    private double afterBalance;

    private String sign;

    public Transaction(int globalTimeUnit,Customer fromCustomer,Customer toCustomer, double amount) throws NegativeBalanceException {
        sign = amount>0? "-":"+";
        this.timeUnit = globalTimeUnit;
        this.toCustomer = toCustomer;
        this.amount = amount; //check in ui that positive
        previousBalance = fromCustomer.getBalance();
        if(previousBalance - amount < 0)
            throw new NegativeBalanceException("Not enough money to complete this transaction");
        else {
            fromCustomer.setBalance(previousBalance - amount);
            afterBalance = previousBalance - amount;
            toCustomer.setBalance(toCustomer.getBalance() + amount);
            if(amount > 0){
                toCustomer.addTransaction(new Transaction(globalTimeUnit,toCustomer,fromCustomer,-amount));
            }
        }

    }

    public Customer getToCustomer() {
        return toCustomer;
    }

    public double getAmount() {
        return amount;
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
