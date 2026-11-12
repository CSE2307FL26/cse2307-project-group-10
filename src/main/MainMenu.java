package main;

import java.util.Scanner;


public class MainMenu {

    private static final int EXIT_SELECTION = 4;
	private static final int MAX_SELECTION = 4;

	private BankAccount userAccount;
    private Scanner keyboardInput;

    public MainMenu() {
        this.userAccount = new BankAccount();
        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");
        
        System.out.println("1. Make a deposit");
        System.out.println("2. Admin: Collect a fee");
        System.out.println("3. Admin: Apply interest");
        System.out.println("3. Exit the app");

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
                performCollectFee();
                break;
            case 3:
                performApplyInterest();
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

    public void performApplyInterest(){
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
