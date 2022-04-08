package menu;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.*;

import bank.*;
import bank.exception.*;
import bank.xml.XmlReader;


import javax.xml.bind.JAXBException;

import static java.lang.Math.abs;

public class Menu {
    public Bank myBank=new Bank();
    private boolean xmlLoadedSuccessfully = false;
    public void run(){
        System.out.println("The current time is: " + Bank.getGlobalTimeUnit());
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
            for(Customer customer:myBank.getCustomers()){
                customer.updateCustomerDto();
            }
            for(Loan loan:myBank.getLoans()){
                loan.updateLoanDto();
            }
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

                else{
                        this.inlayLoan();
                }
                    this.run();
                    break;

                case "7":
                    if(!xmlLoadedSuccessfully)
                        System.out.println("No Xml file is loaded, please load first a Xml file!\n");
                    else{
                        moveTimeLine();
                    }
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

    private void moveTimeLine() {
        try {
            myBank.updateGlobalTimeUnit();
        } catch (NegativeBalanceException e) {
            System.out.println(e);
        }
    }

    private void inlayLoan() {

        Customer customer = chooseCustomer();
        int chosenSum = chooseSumToInvest(customer.getBalance());
        Set<String> chosenCategories = chooseCategories();
        int chosenInterest = chooseMinimumInterestPercent();
        int chosenUnitTime = chooseMinimumTotalUnitTime();
        ArrayList<Loan> possibleLoans =  myBank.checkLoans(customer, chosenCategories, chosenInterest, chosenUnitTime);
        if(!possibleLoans.isEmpty()) {
            ArrayList<Loan> chosenLoans = chooseLoans(possibleLoans);
            if(!chosenLoans.isEmpty()) {
                for (Loan loan : chosenLoans) {
                    double perLoanInvest = chosenSum / chosenLoans.size();
                    try {
                        if(perLoanInvest > loan.getAmountToComplete()){
                            perLoanInvest = loan.getAmountToComplete();
                            loan.addLoaner(customer, perLoanInvest);
                            System.out.println(customer.getName() + " is successfully invest " + perLoanInvest + " in \"" +loan.getLoanName()+"\" loan.");
                        }
                        else {
                            loan.addLoaner(customer, perLoanInvest);
                            System.out.println(customer.getName() + " is successfully invest " + perLoanInvest + " in \"" +loan.getLoanName()+"\" loan.");
                        }
                    } catch (NegativeBalanceException e) {
                        System.out.println(e.toString());
                    }
                }
                System.out.println();
            }
        }
        else{
            System.out.println("Sorry, No loan has been found that matches your preferences :(");
        }
    }

    private ArrayList<Loan> chooseLoans(ArrayList<Loan> possibleLoans) {
        ArrayList<Loan> chosenLoans = new ArrayList<>();
        ArrayList<Loan> tempLoans = possibleLoans;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose a loan to invest by its number:\n");
        Loan chosenLoan = chooseLoanFrom(tempLoans);
        chosenLoans.add(chosenLoan);
        tempLoans.remove(chosenLoan);
        String choice;
        int intChoice=-1;
        while (true && !(tempLoans.isEmpty())) {
            System.out.println("Do you want to invest in more loans?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            choice = scanner.next();
            try {
                intChoice = Integer.parseInt(choice);
                if (intChoice <= 0 || intChoice > 2)
                    System.out.println("Must enter 1 or 2, please enter your choice again:");
            } catch (Exception e) {
                System.out.println("Must enter 1 or 2, please enter your choice again:");
            }
            if (intChoice == 1) {
                chosenLoan = chooseLoanFrom(tempLoans);
                chosenLoans.add(chosenLoan);
                tempLoans.remove(chosenLoan);
            }
            if (intChoice == 2)
                break;

        }
        return chosenLoans;
    }

    private Loan chooseLoanFrom(ArrayList<Loan> tempLoans) {
        HashMap<Integer,Loan> mapLoans= new HashMap<>();
        Integer i = 1;
        for(Loan loan: tempLoans){
            mapLoans.put(i,loan);
            i++;
        }
        String choice = "";
        int intChoice = -1;
        printLoanInfo(myBank.makeDto(tempLoans));
        Scanner scanner = new Scanner(System.in);
        while (true) {
            choice = scanner.next();
            try {
                intChoice = Integer.parseInt(choice);
                if (mapLoans.keySet().contains(intChoice)) {
                    break;
                } else {
                    System.out.println("Must enter a number between 1 to " + mapLoans.size() + ", please enter your choice again:");
                }
            } catch (Exception e) {
                System.out.println("Must enter a number between 1 to " + mapLoans.size() + ", please enter your choice again:");
            }
        }

        Loan chosenLoan = mapLoans.get(intChoice);
        return chosenLoan;

    }

    private int chooseMinimumTotalUnitTime() {
        int chosenTotalUnitTime=1;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to choose a minimum total time of loans to invest?");
        System.out.println("1. Yes");
        System.out.println("2. No, I want to invest at all loans duration rates.");
        String choice;
        int intChoice;
        while (true) {
            choice = scanner.next();
            try {
                intChoice = Integer.parseInt(choice);
                if (intChoice > 0 && intChoice <= 2) {
                    break;
                } else {
                    System.out.println("Must enter 1 or 2, please enter your choice again:");
                }
            } catch (Exception e) {
                System.out.println("Must enter 1 or 2, please enter your choice again:");
            }
        }
        if(intChoice == 1){
            System.out.println("Please enter a minimum duration rate, in time units:");
            while (true) {
                choice = scanner.next();
                try {
                    intChoice = Integer.parseInt(choice);
                    if (intChoice > 0) {
                        chosenTotalUnitTime = intChoice;
                        break;
                    } else {
                        System.out.println("Must enter an integer greater than 0, please enter your choice again:");
                    }
                } catch (Exception e) {
                    System.out.println("Must enter an integer greater than 0, please enter your choice again:");
                }
            }

        }
        return chosenTotalUnitTime;
    }

    private int chooseMinimumInterestPercent() {
        int chosenInterestPercent=0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to choose a minimum interest rate of loans to invest?");
        System.out.println("1. Yes");
        System.out.println("2. No, I want to invest at all interest rates.");
        String choice;
        int intChoice;
        while (true) {
            choice = scanner.next();
            try {
                intChoice = Integer.parseInt(choice);
                if (intChoice > 0 && intChoice <= 2) {
                    break;
                } else {
                    System.out.println("Must enter 1 or 2, please enter your choice again:");
                }
            } catch (Exception e) {
                System.out.println("Must enter 1 or 2, please enter your choice again:");
            }
        }
        if(intChoice == 1){
            System.out.println("Please enter a minimum interest rate:");
            while (true) {
                choice = scanner.next();
                try {
                    int interestChoice = Integer.parseInt(choice);
                    if (interestChoice > 0) {
                        chosenInterestPercent = interestChoice;
                        break;
                    } else {
                        System.out.println("Must enter an integer greater than 0, please enter your choice again:");
                    }
                } catch (Exception e) {
                    System.out.println("Must enter an integer greater than 0, please enter your choice again:");
                }
            }

        }
        return chosenInterestPercent;
    }

    private Set<String> chooseCategories() {
        Set<String> tempCategories = myBank.getCategory();
        Set<String> chosenCategories = new HashSet<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to choose a category of loan to invest?");
        System.out.println("1. Yes");
        System.out.println("2. No, I want to invest all categories.");
        String tempCategory="";
        String choice;
        int intChoice;
        while (true) {
            choice = scanner.next();
            try {
                intChoice = Integer.parseInt(choice);
                if (intChoice > 0 && intChoice <= 2) {
                    break;
                } else {
                    System.out.println("Must enter 1 or 2, please enter your choice again:");
                }
            } catch (Exception e) {
                System.out.println("Must enter 1 or 2, please enter your choice again:");
            }
        }
        if(intChoice == 1) {
            tempCategory = chooseCategoryFrom(tempCategories);
            chosenCategories.add(tempCategory);
            tempCategories.remove(tempCategory);
            while (true && !(tempCategories.isEmpty())) {
                System.out.println("Do you want to add more categories?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                choice = scanner.next();
                try {
                    intChoice = Integer.parseInt(choice);
                    if (intChoice <= 0 || intChoice > 2)
                        System.out.println("Must enter 1 or 2, please enter your choice again:");
                }
                 catch (Exception e) {
                    System.out.println("Must enter 1 or 2, please enter your choice again:");
                }
                if (intChoice == 1)
                    tempCategory = chooseCategoryFrom(tempCategories);
                    chosenCategories.add(tempCategory);
                    tempCategories.remove(tempCategory);
                if(intChoice == 2)
                    break;
            }
        }
        else {
            chosenCategories =  tempCategories;

        }
        return  chosenCategories;
    }

    private String chooseCategoryFrom(Set<String> tempCategories) {
        HashMap<Integer,String> mapCategories= new HashMap<>();
        Integer i = 1;
        for(String category: tempCategories){
            mapCategories.put(i,category);
            i++;
        }
        String choice = "";
        int intChoice = -1;
        System.out.println("Please choose a category to invest:");
        for(Integer num:mapCategories.keySet()){
            System.out.println(num+". "+ mapCategories.get(num));
        }
        Scanner scanner = new Scanner(System.in);

        while (true) {
            choice = scanner.next();
            try {
                intChoice = Integer.parseInt(choice);
                if (mapCategories.keySet().contains(intChoice)) {
                    break;
                } else {
                    System.out.println("Must enter a number between 1 to " + mapCategories.size() + ", please enter your choice again:");
                }
            } catch (Exception e) {
                System.out.println("Must enter a number between 1 to " + mapCategories.size() + ", please enter your choice again:");
            }
        }

        return mapCategories.get(intChoice);
    }

    private int chooseSumToInvest(double balance) {
        System.out.println("Please enter the amount you are interested to invest:");
        int intAmount;
        while (true) {
           Scanner scanner=new Scanner(System.in);
            String amount = scanner.next();
            try {
                intAmount = Integer.parseInt(amount);
                if (intAmount <= balance && intAmount>0) {

                    break;
                } else {
                    if (!(intAmount <= balance)){
                        System.out.println("The amount is Blocked By You Balance with is " + balance + " !, please enter amount again:");
                }else{
                        System.out.println("The amount for a deposit must be a positive integer!, please enter amount again:");
                    }

                }
            } catch (InputMismatchException e) {
                System.out.println("The amount for a deposit must be a positive integer!, please enter amount again:");
            } catch (NumberFormatException e) {
                System.out.println("The amount for a withdraw must be a positive integer!, please enter amount again:");
            }
            }
        return intAmount;
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
            } catch (NotInCategoryException e) {
                System.out.println(e.toString());
            } catch (AlreadyExistCustomerException e) {
                System.out.println(e.toString());
            }
        }

        System.out.println("File loaded successfully!\n");
        xmlLoadedSuccessfully = true;
        myBank = newBank;
    }

    private XmlReader getXmlPath() throws ExitXmlLoadFileException {

        boolean succeed = false;
        XmlReader myXml = null;
        String command = "";
        String xmlString;
        Scanner obj = new Scanner(System.in);

        while (!succeed) {
            if(command.equals("")) {
                System.out.println("Please enter a Xml file path:");
                xmlString = obj.nextLine();
            }else{
                xmlString=command;
            }
            try {
                myXml = new XmlReader(Paths.get(xmlString));
                succeed = true;
            } catch (FileNotFoundException e) {
                System.out.println("File not found! Please try again or press 'Q' to return to the main menu..");
                command = obj.nextLine();
                if (command.equals("q") || command.equals('Q')) {
                    break;
                }
            } catch (NotXmlException e) {
                System.out.println("Not Xml file! Please try again or press 'Q' to return to the main menu..");
                command = obj.nextLine();
                if (command.equals("q") || command.equals('Q')) {
                    break;
                }
            } catch (Exception b) {
                System.out.println("Something went wrong! Please try again or press 'Q' to return to the main menu..");
                command = obj.nextLine();
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
                ", interest: " +(int)(loanDto.getInterestPercent()*100)+"%" +
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
            String activeStr="Become Active at: " + loanDto.getStartTimeUnit();
            if(loanDto.getStatus() != Status.Finished && loanDto.getStatus() != Status.Risk)
                activeStr +=", Next Payment in: "+nextPayment;
            activeStr+="\nThe Payments maid so far:\n";

            for(LoanTransaction transaction:loanDto.getTransactions()){
                activeStr+="Time unit:"+transaction.getTimeUnit()+", Fund:"+transaction.getFundPart()+", Interest:"+transaction.getInterestPart()+" Total: "+(transaction.getInterestPart()+transaction.getFundPart())+"\n";
            }
            activeStr+="Total fund paid:"+loanDto.getCurrentFund()+", Total interest paid:"+loanDto.getCurrentInterest()+", Total remain fund:"+ loanDto.getRemainFund()+" Total remain interest:" +loanDto.getRemainInterest();
            res+=activeStr;
            if(loanDto.getStatus()==Status.Risk){
                String debtsInfo = "\n" + loanDto.getBorrowerName() + "'s debts:\n";
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
                String finishInfo = ", Start time: " + loanDto.getStartTimeUnit()
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
            int i =1;
            for(LoanDto loanDto: loansDto){
                String info = getLoanDtoToString(loanDto);
                System.out.println(i + ". " + info);
                i++;
            }
        }
    }

    public String getCustomerDtoToString(CustomerDto customerDto){
        String res = "Customer's name: " + customerDto.getName() + "\n";
        for(Transaction transaction: customerDto.getTransactions()){
            res += "Time performed: " + transaction.getTimeUnit() +
                    ", Amount transferred: " + transaction.getSign() + abs(transaction.getAmount()) +
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
                    System.out.println("The withdraw made successfully, the new customer's account balance: " + customer.getBalance() + "\n");
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
            System.out.printf("%d. %-10s %.1f%n",  counter, customer.getName(), customer.getBalance());
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
