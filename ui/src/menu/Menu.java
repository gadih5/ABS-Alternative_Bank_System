package menu;

import java.util.Scanner;

public class Menu {
    public static void run(){
        System.out.println("Please choose a command by number:");
        System.out.println("1)Read File");
        System.out.println("2)Show all loans info");
        System.out.println("3)Show customers data");
        System.out.println("4)Deposit to account");
        System.out.println("5)Withdraw from account");
        System.out.println("6)Inlay to a loan");
        System.out.println("7)Move the timeline");
        System.out.println("8)Exit");

        menuController();
    }

    private static void menuController() {
        boolean isError = false;
        do {
            isError = false;
            Scanner scanner = new Scanner(System.in);

            String command = scanner.next();

            switch (command) {
                case "1":

                    break;
                case "2":

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
}
