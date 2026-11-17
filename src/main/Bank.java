package main;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final List<BankAccount> accounts = new ArrayList<>();

    public BankAccount createAccount(){
        BankAccount newAccount = new BankAccount();
        accounts.add(newAccount);
        return newAccount;
}

    public BankAccount getAccount(int index){
        return accounts.get(index);
    }
    
    public int getAccountCount(){
        return accounts.size();
    }

    //Converts typed text into a number, else throws
    public static double parseAmount(String text) {
        try {
            double amount = Double.parseDouble(text.trim());
            if (!Double.isFinite(amount)) {
                throw new IllegalArgumentException();
            }
            return amount;
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }

    //Converts typed text into a menu choice from 1 to max, else throws
    public static int parseSelection(String text, int max) {
        try {
            int selection = Integer.parseInt(text.trim());
            if (selection < 1 || selection > max) {
                throw new IllegalArgumentException();
            }
            return selection;
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }
}


