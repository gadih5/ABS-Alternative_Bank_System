package bank;

import java.util.Collection;

public class CustomerDto {
    private String name;
    private Collection<Transaction> transactions;
    private Collection<Loan> ingoingLoans;
    private Collection<Loan> outgoingLoans;

    public CustomerDto(String name,Collection transactions, Collection ingoingLoans, Collection outgoingLoans) {
        this.name = name;
        this.transactions = transactions;
        this.ingoingLoans = ingoingLoans;
        this.outgoingLoans = outgoingLoans;
    }

    @Override
    public String toString() {
        String res = "Customer full name: " + this.name + "\n" +
                     "All customer's transactions made so far: " + "\n";
        for(Transaction transaction: this.transactions){
            res += "Time performed: " + transaction.getTimeUnit() +
                    "Amount transferred: " + transaction.getSign() + transaction.getAmount() +
                    "Previous balance: " + transaction.getPreviousBalance() +
                    "After balance: " + transaction.getAfterBalance() + "\n";
        }

        res += "All loans that the customer has borrowed: " + "\n";
        for(Loan loan: this.outgoingLoans){
            res += "Loan name: " + loan.getLoanName() +
                    "Category: " + loan.getReason() +
                    "Fund: " + loan.getLoanSum() +
                    "Payment frequency: " + loan.getPaymentFrequency() +
                    "Interest: " + loan.getInterestPercent() +
                    "Final loan's amount: " + loan.getStartLoanAmount() +
                    "Status: " + loan.getStatus();
            if(loan.getStatus() == Status.Pending){
                res += "Amount missing become 'Active': " + loan.getAmountToComplete();
            }
            if(loan.getStatus() == Status.Active){
                res += "Next payment: " + checkNextPayment(loan.getStartTimeUnit(), loan.getPaymentFrequency());
            }

        }





                ", ingoingLoans=" + ingoingLoans +
                ", outgoingLoans=" + outgoingLoans +
                '}';



        return res;
    }

    public int checkNextPayment(int startTimeUnit, int paymentFrequency) {
        int nextPayment;
        if((Bank.getGlobalTimeUnit() - startTimeUnit)%paymentFrequency==0 || paymentFrequency == 1){
            nextPayment = Bank.getGlobalTimeUnit();
        }
        if(Bank.getGlobalTimeUnit() == startTimeUnit){
            nextPayment = Bank.getGlobalTimeUnit() + paymentFrequency;
        }
        else{
            nextPayment = (Bank.getGlobalTimeUnit() - startTimeUnit)%paymentFrequency + Bank.getGlobalTimeUnit();
        }
        return nextPayment;
    }
}
