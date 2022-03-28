package menu;

import java.util.Collection;
import java.util.Scanner;

import bank.*;

public class Menu {
    public Bank myBank=new Bank();
    public void run(){
        System.out.println("Please choose a command by number:");
        System.out.println("1)Read File");
        System.out.println("2)Show all loans info");
        System.out.println("3)Show customers data");
        System.out.println("4)Deposit to account");
        System.out.println("5)Withdraw from account");
        System.out.println("6)Inlay to a loan");
        System.out.println("7)Move the timeline");
        System.out.println("8)Exit");

        this.menuController();
    }

    private void menuController() {
        boolean isError = false;
        do {
            isError = false;
            Scanner scanner = new Scanner(System.in);

            String command = scanner.next();

            switch (command) {
                case "1":

                    break;
                case "2":
                    this.printLoanInfo(myBank.getLoansDto());

                    break;
                case "3":

                    break;
                case "4":

                    break;
                case "5":

                    break;
                case "6":

                    break;
                case "7":

                    break;
                case "8":

                    break;
                default:
                    System.out.println("Must be a number from 1 to 8, please enter your choise again:");
                    isError = true;
            }
        }while(isError);
    }


    public String getLoanDtotoString(LoanDto loanDto){
        String res=    "Loan Name: "+ loanDto.getLoanName() +
                ", Borrower Name: " + loanDto.getBorrowerName() +
                ", Reason: " + loanDto.getReason() +
                ", Loan Sum: " + loanDto.getLoanSum() +
                ", Total Loan Time: " + loanDto.getTotalTimeUnit() +
                ", interest: " + loanDto.getInterestPrecent() +
                ", Payment Frequency: " + loanDto.getPaymentFrequency() +
                ", Status: " + loanDto.getStatus() +"\n\r";
        String frqString="Loaners: \n";
        for(Fraction frq:loanDto.getFractions()){
            frqString +=frq.toString();
        }
        res+=frqString;
        if(loanDto.getStatus()== Status.Pending){
            res+="\nThe total amount that being collect so far";
            res+="\nThe remaining amount to activate the loan: "+loanDto.getAmountToComplete();

        }else{
            int nextPayment = loanDto.checkNextPayment();
            String activeStr="Become Active at: " + loanDto.getStartTimeUnit() + "Next Payment in: "+nextPayment;
            activeStr+="/nThe Payments maid so far:\n";

            for(LoanTransaction transaction:loanDto.getTransactions()){
                activeStr+="Time unit:"+transaction.getTimeUnit()+" Fund:"+transaction.getFundPart()+" Interest:"+transaction.getInterestPart()+" Total: "+(transaction.getInterestPart()+transaction.getFundPart())+"\n";
            }
            activeStr+="Total fund paid:"+loanDto.getCurrentFund()+" Total interest paid:"+loanDto.getCurrentInterest()+" Total remain fund:"+ loanDto.getRemainFund()+" Total remain interest:" +loanDto.getRemainInterest();
            res+=activeStr;
            if(loanDto.getStatus()==Status.Risk){
                String debtsInfo = loanDto.getBorrowerName() + "debts: ";
                double debtAmount = 0;
                int numOfDebts = 0;
                for(Debt debt: loanDto.getDebts()){
                    debtsInfo += debt.toString() + "\n";
                    debtAmount += debt.getAmount();
                    numOfDebts++;
                }
                debtsInfo += "Number of debts: " + numOfDebts
                        +", Sum of debts: " + debtAmount + "\n";
                res += debtsInfo;
            }
            if(loanDto.getStatus()==Status.Finished) {
                String finishInfo = "Start time: " + loanDto.getStartTimeUnit()
                        + ", Finish time: " + loanDto.getFinishTimeUnit() + "\n";
                res += finishInfo;
            }
        }
        return res;
    }
    public void printLoanInfo(Collection<LoanDto> loansDto){
        for(LoanDto loanDto: loansDto){
            String info = getLoanDtotoString(loanDto);
            System.out.println(info);
        }
    }
}
