package menu;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;

import bank.*;
import bank.exception.NotXmlException;
import bank.xml.XmlReader;

import javax.xml.bind.JAXBException;

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
        do {
            Scanner scanner = new Scanner(System.in);

            String command = scanner.next();

            switch (command) {
                case "1":
                    this.getXmlPath();
                    this.run();
                    break;

                case "2":
                    this.printLoanInfo(myBank.getLoansDto());
                    this.run();
                    break;

                case "3":

                    this.printCustomerInfo(myBank.getCustomersDto());
                    this.run();
                    break;

                case "4":
                    this.depositMoneyToCustomer();
                    this.run();
                    break;

                case "5":

                    break;
                case "6":

                    break;
                case "7":

                    break;
                case "8":
                    System.out.println("Thank you for using ABS, cya next time!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Must be a number between 1 to 8, please enter your choice again:\n");
            }
        }while(true);
    }

    private void getXmlPath() {

        boolean succeed = false;
        while(!succeed) {
            System.out.println("Please enter a Xml file path:\n");
            String xmlString;
            Scanner obj = new Scanner(System.in);
            xmlString = obj.nextLine();
            try {
                XmlReader myXml = new XmlReader(Paths.get(xmlString));
                succeed = true;
            } catch (FileNotFoundException e) {
                System.out.println("File not found! Please try again..");

            } catch (NotXmlException e) {
                System.out.println("Not Xml file! Please try again..");
            } catch (Exception b) {
                System.out.println("Something went wrong! Please try again..");
            }
        }
        System.out.println("File loaded successfully!\n");
    }


    public String getLoanDtoToString(LoanDto loanDto){
        String res=    "Loan Name: "+ loanDto.getLoanName() +
                ", Borrower Name: " + loanDto.getBorrowerName() +
                ", Reason: " + loanDto.getReason() +
                ", Loan Sum: " + loanDto.getLoanSum() +
                ", Total Loan Time: " + loanDto.getTotalTimeUnit() +
                ", interest: " + loanDto.getInterestPercent() +
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
        if(loansDto == null){
            System.out.println("There is no any loan in the system.\n");
        }
        else{
            for(LoanDto loanDto: loansDto){
                String info = getLoanDtoToString(loanDto);
                System.out.println(info);
            }
        }
    }

    public String getCustomerDtoToString(CustomerDto customerDto){
        String res = "Customer full name: " + customerDto.getName() + "\n" +
                "All customer's transactions made so far: " + "\n";
        for(Transaction transaction: customerDto.getTransactions()){
            res += "Time performed: " + transaction.getTimeUnit() +
                    "Amount transferred: " + transaction.getSign() + transaction.getAmount() +
                    "Previous balance: " + transaction.getPreviousBalance() +
                    "After balance: " + transaction.getAfterBalance() + "\n";
        }
        if(!customerDto.getOutgoingLoans().isEmpty()) {
            res += "All loans that the customer has borrowed: " + "\n";
            res += customerDto.getCustomerLoansInfo(customerDto.getOutgoingLoans());
        }
        if(!customerDto.getIngoingLoans().isEmpty()) {
            res += "All loans that the customer is a loaner: " + "\n";
            res += customerDto.getCustomerLoansInfo(customerDto.getIngoingLoans());
        }
        return res;
    }

    public void printCustomerInfo(Collection<CustomerDto> customersDto){
        if(customersDto == null){
            System.out.println("There is no any customer in the system.\n");
        }
        else{
            for(CustomerDto customerDto: customersDto){
                String info = getCustomerDtoToString(customerDto);
                System.out.println(info);
            }
        }

    }

    public void depositMoneyToCustomer(){
        if(myBank.getCustomers().isEmpty()){
            System.out.println("There are no customers in the system!\n");
        }
        else{
          chooseCustomer();
        }
    }

    public void chooseCustomer(){

        int counter = 1;
        System.out.println("List of all the customers, choose a customer by number:\n");
        for(Customer customer: myBank.getCustomers()){
            System.out.println(counter + ". " + customer.getName() + "\n");
            counter++;
        }
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        while(!(choice > 0 && choice < counter)){
            System.out.println("Must enter a number between 1 to " + (counter-1) + ", please enter your choice again:");
            choice = scanner.nextInt();
        }
        Customer customer = myBank.getCustomers().get(choice-1);
        System.out.println("Enter an amount for a deposit to " + customer.getName() +"'s account:");
        int amount = scanner.nextInt();
        while(!(amount <= 0)){
            System.out.println("The amount for a deposit must be a positive integer!, please enter amount again:");
            amount = scanner.nextInt();
        }
        customer.depositToAccount(amount);
        System.out.println("The deposit made successfully, the new customer's account balance: " + customer.getBalance());
    }


}
