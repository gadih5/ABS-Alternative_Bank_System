package bank;
import bank.Customer;
import bank.exception.NegativeBalanceException;

public class Transaction {
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
        sign = amount>0? "+":"";
        boolean amountChange = false;
        this.previousBalance = fromCustomer.getBalance();
        this.timeUnit = Bank.getGlobalTimeUnit();
        this.toCustomer = toCustomer;
        this.amount = amount; //check in ui that positive
        if(fromCustomer == toCustomer){//its a self deposit
            afterBalance = previousBalance + amount;
            toCustomer.setBalance(afterBalance);
        }
        else {
            previousBalance = fromCustomer.getBalance();
            if (previousBalance - amount < 0)
                throw new NegativeBalanceException("Not enough money to complete this transaction");
            else {
                if(amount < 0 && firstTime) {
                    amount = -amount;
                    amountChange = true;
                }
                fromCustomer.setBalance(previousBalance - amount);
                afterBalance = previousBalance - amount;
                toCustomer.setBalance(toCustomer.getBalance() + amount);
                if(amountChange)
                    amount = -amount;
                if (firstTime)
                    toCustomer.addTransaction(new Transaction(toCustomer, fromCustomer, -amount,false));

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
