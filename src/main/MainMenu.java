package main;

import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 10;
    private static final int MAX_SELECTION = 10;

    private BankAccount userAccount;
    private Bank accountManager;
    private Scanner keyboardInput;
    private AccountMenu accountMenu;

    public MainMenu() {
        this.accountManager = new Bank();
        this.userAccount = accountManager.createAccount();
        this.keyboardInput = new Scanner(System.in);
        this.accountMenu = new AccountMenu(keyboardInput);
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");
        System.out.println("Current account: " + userAccount.getAccountName());
        System.out.println("1. Make a deposit");
        System.out.println("2. View transaction history");
        System.out.println("3. Create a new account");
        System.out.println("4. Switch account");
        System.out.println("5. Make a withdrawal");
        System.out.println("6. Check account balance");
        System.out.println("7. Transfer money");
        System.out.println("8. Close account");
        System.out.println("9. Rename account");
        System.out.println("10. Back to role selection");
    }

    public int getUserSelection(int max) {
        while (true) {
            System.out.print("Please make a selection: ");
            try {
                return Bank.parseSelection(keyboardInput.nextLine(), max);
            } catch (IllegalArgumentException e) {
                System.out.println("Please enter a number from 1 to " + max + ".");
            }
        }
    }

    public double getUserAmount() {
        while (true) {
            try {
                return Bank.parseAmount(keyboardInput.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    private int selectRole() {
        System.out.println("Choose your role:");
        System.out.println("1. Customer");
        System.out.println("2. Admin");
        System.out.println("3. Exit");
        return getUserSelection(3);
    }

    public void processInput(int selection) {
        switch (selection) {
            case 1:
                performDeposit();
                break;
            case 2:
                displayTransactionHistory();
                break;
            case 3:
                createAdditionalAccount();
                break;
            case 4:
                switchAccount();
                break;
            case 5:
                performWithdrawal();
                break;
            case 6:
                performCheckBalance();
                break;
            case 7:
                performTransfer();
                break;
            case 8:
                performClose();
                break;
            case 9:
                accountMenu.performRename(accountManager);
                break;
        }
    }

    private void printAccounts() {
        for (int i = 0; i < accountManager.getAccountCount(); i++) {
            BankAccount account = accountManager.getAccount(i);
            String status = account.isClosed() ? " (closed)" : "";
            System.out.println((i + 1) + ". " + account.getAccountName() + status);
        }
    }

    public void performDeposit() {
        System.out.print("How much would you like to deposit: ");
        double depositAmount = getUserAmount();

        try {
            userAccount.deposit(depositAmount);
            System.out.println("Deposit complete");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid deposit");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    public void performTransfer() {
        if (accountManager.getAccountCount() < 2) {
            System.out.println("You need at least two accounts");
            return;
        }

        System.out.println("Choose account to transfer FROM:");
        printAccounts();
        int from = getUserSelection(accountManager.getAccountCount());

        System.out.println("Choose account to transfer TO:");
        printAccounts();
        int to = getUserSelection(accountManager.getAccountCount());
        System.out.println("How much would you like to transfer?");
        double amount = getUserAmount();

        try {
            BankAccount source = accountManager.getAccount(from - 1);
            BankAccount destination = accountManager.getAccount(to - 1);

            source.transferTo(destination, amount);
            System.out.printf("Transferred $%.2f from %s to %s%n", amount, source.getAccountName(),
                    destination.getAccountName());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void performCheckBalance() {
        for (int i = 0; i < accountManager.getAccountCount(); i++) {
            BankAccount account = accountManager.getAccount(i);
            System.out.printf("%d. %s: $%.2f%n",
                    i + 1, account.getAccountName(), account.getBalance());
        }
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction history for " + userAccount.getAccountName() + ":");
        List<String> history = userAccount.getTransactionHistory();

        if (history.isEmpty()) {
            System.out.println("No transactions have been made.");
            return;
        }

        for (String transaction : history) {
            System.out.println(transaction);
        }
    }

    public void createAdditionalAccount() {
        userAccount = accountManager.createAccount();
        System.out.println(userAccount.getAccountName() + " created and selected.");
    }

    public void switchAccount() {
        System.out.println("Choose an account:");
        printAccounts();

        int selection = getUserSelection(accountManager.getAccountCount());
        userAccount = accountManager.getAccount(selection - 1);

        System.out.println("Selected " + userAccount.getAccountName());
    }

    public void performWithdrawal() {
        if (userAccount.getBalance() <= 0) {
            System.out.println(
                    "You don't have the available funds to make a withdrawal.");
            return;
        }

        System.out.print("How much would you like to withdraw: ");
        double withdrawAmount = getUserAmount();

        if (withdrawAmount <= 0 || withdrawAmount > userAccount.getBalance()) {
            System.out.println(
                    "You don't have the available funds to make a withdrawal.");
        } else {
            userAccount.withdraw(withdrawAmount);
        }
    }

    public void performCollectFee() {
        System.out.print("Enter the fee amount to collect: ");
        double feeAmount = getUserAmount();

        try {
            userAccount.collectFee(feeAmount);
            System.out.printf(
                    "Fee collected. New balance: $%.2f%n",
                    userAccount.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid fee amount. No fee was collected.");
        }
        catch (IllegalStateException e) {
            System.out.println(e.getMessage() + ". No fee was collected.");

        }
    }

    public void performApplyInterest() {
        System.out.print("Enter the interest rate as a percent (e.g. 5 for 5%): ");
        double ratePercent = getUserAmount();

        try {
            userAccount.applyInterest(ratePercent / 100);
            System.out.printf(
                    "Interest added. New balance: $%.2f%n",
                    userAccount.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid interest rate. No interest was added.");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage() + ". No interest was added.");
        }
    }

    public void performClose() {
        try {
            userAccount.close();
            System.out.println(userAccount.getAccountName() + " closed");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        AdminMenu adminMenu = new AdminMenu();

        while(true){
            int role = selectRole();

        while(role == 2){
            adminMenu.displayOptions(userAccount.getAccountName());
            int choice = getUserSelection(4);

            if(choice == 1){
                performCollectFee();
            }
            else if(choice == 2){
                performApplyInterest();
            }
            else if(choice == 3){
                switchAccount();
            }
            else if(choice == 4){
                role = selectRole();
            }
            else{
                System.out.println("Invalid selection");
            }
        }

        if(role == 3){
            return;
        }

        int selection = -1;

        while (selection != EXIT_SELECTION) {
            displayOptions();
            selection = getUserSelection(MAX_SELECTION);
            processInput(selection);
        }
    }
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }
}