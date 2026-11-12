package main;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class MainMenu {

    private static final int EXIT_SELECTION = 4;
	private static final int MAX_SELECTION = 4;

	private BankAccount userAccount;
    private Scanner keyboardInput;
    private ArrayList<BankAccount> accounts;

    public MainMenu() {
        this.userAccount = new BankAccount();
        this.keyboardInput = new Scanner(System.in);
        this.accounts = new ArrayList<BankAccount>();
        this.accounts.add(userAccount);
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");
        
        System.out.println("1. Make a deposit");
        System.out.println("2. Check account balance");
        System.out.println("3. View transaction history");
        System.out.println("4. Exit the app");
    }

    public int getUserSelection(int max) {
        int selection = -1;
        while(selection < 1 || selection > max) {
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
                performCheckBalance();
                break;
		    case 3:
                displayTransactionHistory();
                break;
        }
    }

    public void performDeposit() {
        double depositAmount = -1;
        while(depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextInt();
        }
        userAccount.deposit(depositAmount);
    }

    public void performCheckBalance() {
        for (int i = 0; i < accounts.size(); i++) {
            double balance = accounts.get(i).getBalance();
            System.out.println("Account " + (i + 1) + ": $" + String.format("%.2f", balance));
        }
    }
    public void displayTransactionHistory() {
        List<String> history = userAccount.getTransactionHistory();
        if(history.isEmpty()) {
            System.out.println("No transactions have been made.");
            return;
        }
        for (String transaction : history) {
            System.out.println(transaction);
        }
    }

    public void run() {
        int selection = -1;
        while(selection != EXIT_SELECTION) {
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
