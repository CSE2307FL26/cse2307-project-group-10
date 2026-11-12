package main;

public class BankAccount {

    private double balance;

    public BankAccount() {
        this.balance = 0;
    }

    public void deposit(double amount) {
        if(amount > 0) {
            this.balance += amount;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double getBalance() {
        return this.balance;
    }

    public void collectFee(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException();
        }
        this.balance -= amount;
    }

    public void applyInterest(double rate) {
        if (rate <= 0) {
            throw new IllegalArgumentException();
        }
        if (this.balance < 0) {
            throw new IllegalStateException();
        }
        
        this.balance += this.balance * rate;
    }
}
