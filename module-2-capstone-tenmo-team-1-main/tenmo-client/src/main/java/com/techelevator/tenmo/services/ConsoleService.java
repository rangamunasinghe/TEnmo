package com.techelevator.tenmo.services;


import com.techelevator.tenmo.exceptions.InvalidOptionException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public int promptForArrayMenuSelection(String prompt, int arrayLength) {
        int menuSelection = -1;
        boolean validOption = false;
        while (!validOption) {
            System.out.print(prompt);
            try {
                menuSelection = Integer.parseInt(scanner.nextLine()) -1;
                if (menuSelection >= arrayLength || menuSelection < 0) {
                    throw new InvalidOptionException();
                }
                validOption = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Option Selection");
            } catch (InvalidOptionException g) {
                System.out.println(g.getMessage());
            }
        }
            return menuSelection;
    }

    public void displayAndSelectAccounts(Account[] accounts) {
        System.out.println("");
        for (int i = 0; i < accounts.length; i++) {
            System.out.println(i+1 + ": Acct No. " + accounts[i].getAccountId());
        }
    }

    public void displayAndSelectUsers(User[] users){
        System.out.println("");
        for (int i = 0; i < users.length; i++) {
            System.out.println(i+1 + ": " + users[i].getUsername());
        }
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View transfer by transfer id");
        System.out.println("4: View your pending requests");
        System.out.println("5: Send TE bucks");
        System.out.println("6: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print("");
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

}
