package main;

import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 4;
	private static final int MAX_SELECTION = 4;

    private ArrayList<BankAccount> accounts;
    private Scanner keyboardInput;

    public MainMenu() {
    this.accounts = new ArrayList<BankAccount>();
        this.accounts.add(new BankAccount());
        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");
        
        System.out.println("1. Make a deposit");
        System.out.println("2. Create another account");
        System.out.println("3. Transfer money");
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
                accounts.add(new BankAccount());
                System.out.println("New account created");
                break;

            case 3:
                performTransfer();
                break;
        }
    }

    public void performDeposit() {
        System.out.println("Choose an account:");
        int account = getUserSelection(accounts.size());

        System.out.print("How much would you like to deposit: ");
        double depositAmount = keyboardInput.nextDouble();

        try {
            accounts.get(account - 1).deposit(depositAmount);
            System.out.println("Deposit complete");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid deposit");
        }
    }

    public void performTransfer() {
        if (accounts.size() < 2) {
            System.out.println("You need at least two accounts");
            return;
        }

        System.out.println("Choose account to transfer FROM:");
        int from = getUserSelection(accounts.size());

        System.out.println("Choose account to transfer TO:");
        int to = getUserSelection(accounts.size());

        System.out.println("How much would you like to transfer?");
        double amount = keyboardInput.nextDouble();

        try {
            accounts.get(from - 1).transferTo(accounts.get(to - 1), amount);
            System.out.println("Transfer complete");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
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
