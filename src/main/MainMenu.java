package main;

import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 4;
	private static final int MAX_SELECTION = 4;
    private AccountManager accountManager;

	private BankAccount userAccount;
    private Scanner keyboardInput;

    public MainMenu() {
        this.accountManager = new AccountManager();
        this.userAccount = accountManager.createAccount();
        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");
        
        System.out.println("1. Make a deposit");
        System.out.println("2. Create a new account");
        System.out.println("3. Switch account");
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
                createAdditionalAccount();
                break;
            case 3:
                switchAccount();
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

    public void createAdditionalAccount(){
        userAccount = accountManager.createAccount();
        System.out.println("Account " + accountManager.getAccountCount() + " created and selected.");
    }

    public void switchAccount(){
        for(int i = 0; i < accountManager.getAccountCount(); i++){
            System.out.println("Account " + (i + 1));
        }
        int selection = getUserSelection(accountManager.getAccountCount());
        userAccount = accountManager.getAccount(selection - 1);
        System.out.println("Selected Account " + selection);
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
