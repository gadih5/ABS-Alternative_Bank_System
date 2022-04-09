package bank;

import java.io.Serializable;
import java.util.Collection;

public class CustomerDto implements Serializable {
    private String name;
    private Collection<Transaction> transactions;
    private Collection<Loan> ingoingLoans;
    private Collection<Loan> outgoingLoans;

    public CustomerDto(Customer customer) {
        this.name = customer.getName();
        this.transactions = customer.getTransactions();
        this.ingoingLoans = customer.getIngoingLoans();
        this.outgoingLoans = customer.getOutgoingLoans();
    }


    public String getCustomerLoansInfo(Collection<Loan> loans){
        String res = "";

        for(Loan loan: loans){
            res += "Loan name: " + loan.getLoanName() +
                    ", Category: " + loan.getReason() +
                    ", Fund: " + loan.getLoanSum() +
                    ", Payment frequency: " + loan.getPaymentFrequency() +
                    ", Interest: " + (int)(loan.getInterestPercent()*100) + "%" +
                    ", Final loan's amount: " + loan.getStartLoanAmount() +
                    ", Status: " + loan.getStatus();
            if(loan.getStatus() == Status.Pending){
                res += ", Amount missing become 'Active': " + loan.getAmountToComplete();
            }
            if(loan.getStatus() == Status.Active) {
                res += ", Next payment at: " + checkNextPayment(loan.getStartTimeUnit(), loan.getPaymentFrequency());

                double nextPayment = 0;
                for (Fraction fraction : loan.getFractions()) {
                    double fundPart = fraction.getAmount() / (loan.getTotalTimeUnit() / loan.getPaymentFrequency());
                    double interestPart = fraction.getAmount() * loan.getInterestPercent() / (loan.getTotalTimeUnit() / loan.getPaymentFrequency());
                    nextPayment += fundPart + interestPart;
                }
                res += ", sum to pay: " + nextPayment;
            }
            if(loan.getStatus() == Status.Risk){
                res += ", Number of debts: ";
                int numberOfDebts = 0;
                double amountOfDebts = 0;
                for(Debt debt:loan.getUncompletedTransactions()){
                    numberOfDebts++;
                    amountOfDebts += debt.getAmount();
                }
                res += numberOfDebts + ", amount to pay: " + amountOfDebts;
            }
            if(loan.getStatus() == Status.Finished){
                res += ", Start time: " + loan.getStartTimeUnit()
                        + ", Finish time: " + loan.getFinishTimeUnit();
            }
            res+="\n";
        }
        return res;
    }


    public String getName() {
        return name;
    }

    public Collection<Transaction> getTransactions() {
        return transactions;
    }

    public Collection<Loan> getIngoingLoans() {
        return ingoingLoans;
    }

    public Collection<Loan> getOutgoingLoans() {
        return outgoingLoans;
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
