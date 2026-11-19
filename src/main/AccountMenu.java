
package main;

import java.util.Scanner;
import java.util.ArrayList;

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
         int selection;
        try {
            selection = Bank.parseSelection(keyboardInput.nextLine(), bank.getAccountCount());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid account selection");
            return;
        }
        BankAccount account = bank.getAccount(selection - 1);

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

    public void performListAccounts(Bank bank) {
    ArrayList<String> summaries = bank.getAccountSummaries();

    if (summaries.isEmpty()) {
        System.out.println("No bank accounts available.");
        return;
    }

    System.out.println("----- All Bank Accounts -----");

    for (String summary : summaries) {
        System.out.println(summary);
        System.out.println();
    }

    System.out.println("Total Accounts: " + summaries.size());
}
}
