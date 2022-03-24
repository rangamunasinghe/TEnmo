package com.techelevator.tenmo;

import com.techelevator.tenmo.exceptions.IllegalTransferAmountException;
import com.techelevator.tenmo.exceptions.InsufficientFundsException;
import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;
import com.techelevator.tenmo.exceptions.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService(API_BASE_URL);
    private final UserService userService = new UserService(API_BASE_URL);
    private final TransferService transferService = new TransferService(API_BASE_URL);

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewTransferById();
            } else if (menuSelection == 4) {
                viewPendingRequests();
            } else if (menuSelection == 5) {
                sendBucks();
            } else if (menuSelection == 6) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance() {
        // TODO Auto-generated method stub
        Account[] accounts = accountService.getAllAccounts(currentUser.getToken());
        consoleService.displayAndSelectAccounts(accounts);
        int selection = consoleService.promptForArrayMenuSelection("Please enter the number to left of desired account: ", accounts.length);
        BigDecimal balance = accounts[selection].getAccountBalance();
        Long id = accounts[selection].getAccountId();
        System.out.println("Balance for account " + id + " is: $" + balance);

    }

    private void viewTransferHistory() {
        // TODO Auto-generated method stub
        Transfer[] userTransfers = transferService.listTransfersByUser(currentUser.getToken());
        for (Transfer transfer : userTransfers) {
            System.out.println(transfer.toString());
        }

    }

    private void viewTransferById() {
        Long id = Long.valueOf(consoleService.promptForInt("Please enter a transfer id to view transfer: "));
        Transfer transfer = transferService.getTransferById(id, currentUser.getToken());
        if (transfer != null) {
            System.out.println(transfer.toString());
        } else {
            System.out.println("TransferId does not exist");
        }
    }

    private void viewPendingRequests() {
        // TODO Auto-generated method stub

    }

    private void sendBucks() {
        // TODO Auto-generated method stub
        //getting and displaying all accounts for logged in user and receiving account selection for transfer
        Account[] userAccounts = accountService.getAllAccounts(currentUser.getToken());
        consoleService.displayAndSelectAccounts(userAccounts);
        int selection = consoleService.promptForArrayMenuSelection("Please enter the number to left of desired account: ", userAccounts.length);
        Long senderAccountId = userAccounts[selection].getAccountId();
        BigDecimal accountBalance = userAccounts[selection].getAccountBalance();


        //getting user input for amount to transfer
        BigDecimal transferAmount = getTransferAmountFromUser(accountBalance);

        //getting and displaying all users. Receiving user selection of recipient
        User[] users = userService.getAllUsersBut(currentUser.getToken());
        consoleService.displayAndSelectUsers(users);
        selection = consoleService.promptForArrayMenuSelection("Please enter the number to left of desired user: ", users.length);
        String recipientUsername = users[selection].getUsername();

        //getting recipient account
        Account[] recipientAccounts = accountService.getAllAccounts(recipientUsername, currentUser.getToken());
        consoleService.displayAndSelectAccounts(recipientAccounts);
        selection = consoleService.promptForArrayMenuSelection("Please enter the number to left of desired account: ", recipientAccounts.length);
        Long recipientAccountId = recipientAccounts[selection].getAccountId();

        //conducting transfer and calling server with post method
        //we catch and display something if transfer doesn't go through
        Transfer currentTransfer = new Transfer(1900L, senderAccountId, recipientAccountId, transferAmount, 2L, 2L);
        Transfer createdTransfer = transferService.createTransfer(currentTransfer, currentUser.getToken(), recipientUsername);
        if (createdTransfer != null) {
            System.out.println("Successful transfer created with ID " + createdTransfer.getTransferId());
        } else {
            System.out.println("Transfer was unsuccessful");
        }

    }


    private void requestBucks() {
        // TODO Auto-generated method stub

    }

    private BigDecimal getTransferAmountFromUser(BigDecimal accountBalance) {
        BigDecimal transferAmount = BigDecimal.ZERO;
        BigDecimal zero = BigDecimal.ZERO;
        Boolean validAmount = false;
        while(!validAmount) {
            try {
                transferAmount = consoleService.promptForBigDecimal("How much money would you like to send? ");
                if (transferAmount.compareTo(zero) < 1) {
                    throw new IllegalTransferAmountException();
                }
                if (accountBalance.compareTo(transferAmount) < 0) {
                    throw new InsufficientFundsException();
                }
                validAmount = true;
            } catch (InsufficientFundsException | IllegalTransferAmountException e) {
                System.out.println(e.getMessage());
            }
        }
        return transferAmount;
    }


}
