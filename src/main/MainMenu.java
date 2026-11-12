package main;

import java.util.List;
import java.util.Scanner;


public class MainMenu {

    private static final int EXIT_SELECTION = 10;
    private static final int MAX_SELECTION = 10;

    private BankAccount userAccount;
    private AccountManager accountManager;
    private Scanner keyboardInput;

    public MainMenu() {
        this.accountManager = new AccountManager();
        this.userAccount = accountManager.createAccount();
        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");
        System.out.println("1. Make a deposit");
        System.out.println("2. View transaction history");
        System.out.println("3. Create a new account");
        System.out.println("4. Switch account");
        System.out.println("5. Make a withdrawal");
        System.out.println("6. Check account balance");
        System.out.println("7. Transfer money");
        System.out.println("8. Admin: Collect a fee");
        System.out.println("9. Admin: Apply interest");
        System.out.println("10. Exit the app");
    }

    public int getUserSelection(int max) {
        int selection = -1;
        while (selection < 1 || selection > max) {
            System.out.print("Please make a selection: ");
            selection = keyboardInput.nextInt();
        }
        return selection;
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
                performCollectFee();
                break;
            case 9:
                performApplyInterest();
                break;
        }
    }

    public void performDeposit() {
        System.out.print("How much would you like to deposit: ");
        double depositAmount = keyboardInput.nextDouble();

        try {
            userAccount.deposit(depositAmount);
            System.out.println("Deposit complete");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid deposit");
        }
    }

    public void performTransfer() {
        if (accountManager.getAccountCount() < 2) {
            System.out.println("You need at least two accounts");
            return;
        }

        System.out.println("Choose account to transfer FROM:");
        for (int i = 0; i < accountManager.getAccountCount(); i++) {
            System.out.println((i + 1) + ". Account " + (i + 1));
        }
        int from = getUserSelection(accountManager.getAccountCount());

        System.out.println("Choose account to transfer TO:");
        int to = getUserSelection(accountManager.getAccountCount());

        System.out.println("How much would you like to transfer?");
        double amount = keyboardInput.nextDouble();

        try {
            BankAccount source = accountManager.getAccount(from - 1);
            BankAccount destination = accountManager.getAccount(to - 1);

            source.transferTo(destination, amount);
            System.out.println("Transfer complete");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void performCheckBalance() {
        for (int i = 0; i < accountManager.getAccountCount(); i++) {
            double balance = accountManager.getAccount(i).getBalance();

            System.out.println(
                "Account " + (i + 1) + ": $" + String.format("%.2f", balance)
            );
        }
    }

    public void displayTransactionHistory() {
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
        System.out.println(
            "Account " + accountManager.getAccountCount() + " created and selected."
        );
    }

    public void switchAccount() {
        for (int i = 0; i < accountManager.getAccountCount(); i++) {
            System.out.println("Account " + (i + 1));
        }

        int selection = getUserSelection(accountManager.getAccountCount());
        userAccount = accountManager.getAccount(selection - 1);

        System.out.println("Selected Account " + selection);
    }

    public void performWithdrawal() {
        if (userAccount.getBalance() <= 0) {
            System.out.println(
                "You don't have the available funds to make a withdrawal."
            );
            return;
        }

        System.out.print("How much would you like to withdraw: ");
        double withdrawAmount = keyboardInput.nextDouble();

        if (withdrawAmount <= 0 || withdrawAmount > userAccount.getBalance()) {
            System.out.println(
                "You don't have the available funds to make a withdrawal."
            );
        } else {
            userAccount.withdraw(withdrawAmount);
        }
    }

    public void performCollectFee() {
        System.out.print("Enter the fee amount to collect: ");
        double feeAmount = keyboardInput.nextDouble();
        try {
            userAccount.collectFee(feeAmount);
            System.out.printf("Fee collected. New balance: $%.2f%n", userAccount.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid fee amount. No fee was collected.");
        }
    }

    public void performApplyInterest() {
        System.out.print("Enter the interest rate as a percent (e.g. 5 for 5%): ");
        double ratePercent = keyboardInput.nextDouble();
        try {
            userAccount.applyInterest(ratePercent / 100);
            System.out.printf("Interest added. New balance: $%.2f%n", userAccount.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid interest rate. No interest was added.");
        } catch (IllegalStateException e) {
            System.out.println("Account balance is negative. No interest was added.");
        }
    }

    public void run() {
        int selection = -1;

        while (selection != EXIT_SELECTION) {
            displayOptions();
            selection = getUserSelection(MAX_SELECTION);
            processInput(selection);
        }
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }
}