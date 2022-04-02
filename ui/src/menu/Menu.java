package menu;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import bank.*;
import bank.exception.*;
import bank.xml.XmlReader;

import javax.xml.bind.JAXBException;

public class Menu {
    public Bank myBank=new Bank();
    private boolean xmlLoadedSuccessfully = false;
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
                    xmlReadAndLoadHandler();
                    this.run();
                    break;

                case "2":
                    if(!xmlLoadedSuccessfully)
                        System.out.println("No Xml file is loaded, please load first a Xml file!\n");
                    else {
                        this.printLoanInfo(myBank.getLoansDto());
                    }
                    this.run();
                    break;

                case "3":
                    if(!xmlLoadedSuccessfully)
                        System.out.println("No Xml file is loaded, please load first a Xml file!\n");
                    else {
                        this.printCustomerInfo(myBank.getCustomersDto());
                    }
                    this.run();
                    break;

                case "4":
                    if(!xmlLoadedSuccessfully)
                        System.out.println("No Xml file is loaded, please load first a Xml file!\n");
                    else {
                        this.depositMoneyToCustomer();
                    }
                    this.run();
                    break;

                case "5":
                    if(!xmlLoadedSuccessfully)
                        System.out.println("No Xml file is loaded, please load first a Xml file!\n");
                    else{
                        chooseCustomerToWithdraw();
                    }
                    this.run();
                    break;

                case "6":
                    if(!xmlLoadedSuccessfully)
                        System.out.println("No Xml file is loaded, please load first a Xml file!\n");




                    this.run();
                    break;

                case "7":
                    if(!xmlLoadedSuccessfully)
                        System.out.println("No Xml file is loaded, please load first a Xml file!\n");




                    this.run();
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

    private void xmlReadAndLoadHandler() {
        boolean res = false;
        Bank newBank = new Bank();
        while(!res) {
            try {
                newBank.loadXmlData(getXmlPath().getDescriptor());
                res = true;
            } catch (NegativeBalanceException e) {
                System.out.println(e.toString());
            } catch (UndefinedReasonException e) {
                System.out.println(e.toString());
            } catch (UndefinedCustomerException e) {
                System.out.println(e.toString());
            } catch (NegativeLoanSumException e) {
                System.out.println(e.toString());
            } catch (NegativeTotalTimeUnitException e) {
                System.out.println(e.toString());
            } catch (NegativeInterestPercentException e) {
                System.out.println(e.toString());
            } catch (NegativePaymentFrequencyException e) {
                System.out.println(e.toString());
            } catch (OverPaymentFrequencyException e) {
                System.out.println(e.toString());
            } catch (UndividedPaymentFrequencyException e) {
                System.out.println(e.toString());
            } catch (ExitXmlLoadFileException e) {
                return;
            }
        }
        xmlLoadedSuccessfully = true;
        myBank = newBank;
    }

    private XmlReader getXmlPath() throws ExitXmlLoadFileException {

        boolean succeed = false;
        XmlReader myXml = null;
        String command = "";
        while (!succeed) {
            System.out.println("Please enter a Xml file path:");
            String xmlString;
            Scanner obj = new Scanner(System.in);
            xmlString = obj.nextLine();
            try {
                myXml = new XmlReader(Paths.get(xmlString));
                succeed = true;
                System.out.println("File loaded successfully!\n");

            } catch (FileNotFoundException e) {
                System.out.println("File not found! Please try again or press 'Q' to return to the main menu..");
                command = obj.next();
                if (command.equals("q") || command.equals('Q')) {
                    break;
                }

            } catch (NotXmlException e) {
                System.out.println("Not Xml file! Please try again or press 'Q' to return to the main menu..");
                command = obj.next();
                if (command.equals("q") || command.equals('Q')) {
                    break;
                }
            } catch (Exception b) {
                System.out.println("Something went wrong! Please try again or press 'Q' to return to the main menu..");
                command = obj.next();
                if (command.equals("q") || command.equals('Q')) {
                    break;
                }
            }
        }
        if(myXml == null && (command.equals("q") || command.equals('Q')))
            throw new ExitXmlLoadFileException();

        return myXml;
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
        if(!(loanDto.getFractions().isEmpty())){
           String frqString="Loaners: \n";
           for(Fraction frq:loanDto.getFractions()){
              frqString +=frq.toString();
           }
           res+=frqString;
        }
        if(loanDto.getStatus()== Status.Pending){
            res+="The total amount that being collect so far: " + loanDto.getCurrentFund();
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
        return res + "\n";
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
        String res = "Customer's name: " + customerDto.getName() + "\n";
        for(Transaction transaction: customerDto.getTransactions()){
            res += "Time performed: " + transaction.getTimeUnit() +
                    ", Amount transferred: " + transaction.getSign() + transaction.getAmount() +
                    ", Previous balance: " + transaction.getPreviousBalance() +
                    ", After balance: " + transaction.getAfterBalance() + "\n";
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
          chooseCustomerToDeposit();
        }
    }

    public void chooseCustomerToDeposit() {
        Scanner scanner = new Scanner(System.in);
        Customer customer = chooseCustomer();
        System.out.println("Enter an amount for a deposit to " + customer.getName() + "'s account:");
        String amount = "";
        int intAmount = -1;

        while (true) {
            amount = scanner.next();
            try {
                intAmount = Integer.parseInt(amount);
                if (intAmount > 0) {
                    customer.selfTransaction(intAmount);
                    System.out.println("The deposit made successfully, the new customer's account balance: " + customer.getBalance() + "\n");
                    break;
                } else {
                    System.out.println("The amount for a deposit must be a positive integer!, please enter amount again:");
                }
            } catch (InputMismatchException e) {
                System.out.println("The amount for a deposit must be a positive integer!, please enter amount again:");
            } catch (NumberFormatException e) {
                System.out.println("The amount for a withdraw must be a positive integer!, please enter amount again:");
            } catch (NegativeBalanceException e) {
            }
        }
    }

    public void chooseCustomerToWithdraw() {
        Scanner scanner = new Scanner(System.in);
        Customer customer = chooseCustomer();
        System.out.println("Enter an amount to withdraw from " + customer.getName() + "'s account:");
        String amount = "";
        int intAmount = -1;

        while (true) {
            amount = scanner.next();
            try {
                intAmount = Integer.parseInt(amount);
                if (intAmount > 0) {
                    customer.selfTransaction(-intAmount);
                    System.out.println("The deposit made successfully, the new customer's account balance: " + customer.getBalance() + "\n");
                    break;
                } else {
                    System.out.println("The amount for a withdraw must be a positive integer!, please enter amount again:");
                }
            } catch (InputMismatchException e) {
                System.out.println("The amount for a withdraw must be a positive integer!, please enter amount again:");
            } catch (NumberFormatException e) {
                System.out.println("The amount for a withdraw must be a positive integer!, please enter amount again:");
            } catch (NegativeBalanceException e) {
                System.out.println(customer.getName() + "'s account doesn't have enough balance to this withdraw!, please enter amount again:");
            }
        }
    }
    private Customer chooseCustomer(){
        int counter = 1;
        String choice = "";
        int intChoice = -1;
        System.out.println("List of all the customers, choose a customer by number:");
        for (Customer customer : myBank.getCustomers()) {
            System.out.println(counter + ". " + customer.getName() + " ,    Balance: " + customer.getBalance());
            counter++;
        }
        Scanner scanner = new Scanner(System.in);

        while (true) {
            choice = scanner.next();
            try {
                intChoice = Integer.parseInt(choice);
                if (intChoice > 0 && intChoice < counter) {
                    break;
                } else {
                    System.out.println("Must enter a number between 1 to " + (counter - 1) + ", please enter your choice again:");
                }
            } catch (Exception e) {
                System.out.println("Must enter a number between 1 to " + (counter - 1) + ", please enter your choice again:");
            }
        }

        Customer customer = myBank.getCustomers().get(intChoice - 1);
        return customer;
    }
}
