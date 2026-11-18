
package main;

import java.util.Scanner;

public class AccountMenu {

    private Scanner keyboardInput;

    public AccountMenu(Scanner keyboardInput) {
        this.keyboardInput = keyboardInput;
    }

    public void performRename(Bank bank) {
        if (bank.getAccountCount() == 0) {
            System.out.println("No accounts available");
            return;
        }
        System.out.println("Choose an account to rename:");

        for (int i = 0; i < bank.getAccountCount(); i++) {
            System.out.println((i + 1) + ". " +
                bank.getAccount(i).getAccountName());
        }
        System.out.print("Choose an account: ");
        int selection = keyboardInput.nextInt();

        if (selection < 1 || selection > bank.getAccountCount()) {
            System.out.println("Invalid account selection");
            return;
        }
        BankAccount account = bank.getAccount(selection - 1);

        keyboardInput.nextLine();
        System.out.print("Enter a new account name: ");
        String name = keyboardInput.nextLine();
        try {
            account.setAccountName(name);
            System.out.println("Account renamed to: " +
                account.getAccountName());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
