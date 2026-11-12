package main;
import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private double balance;
    private final List<String> transactionHistory = new ArrayList<>();

    public BankAccount() {
        this.balance = 0;
    }

    public void deposit(double amount) {
        if(amount > 0) {
            this.balance += amount;
            transactionHistory.add("Deposit: +" + amount + ", Balance: " + balance);   
        } else {
            throw new IllegalArgumentException();
        } 
    }

    //withdraw based on amount taken in parameter, additional check to ensure balance is valid
    public void withdraw(double amount) {
        if(amount > 0 && amount <= this.balance) {
            this.balance -= amount;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double getBalance() {
        return this.balance;
    }

    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }
}
