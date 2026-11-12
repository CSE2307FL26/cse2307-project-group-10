
package main;

public class BankAccount {

    private double balance;

    public BankAccount() {
        this.balance = 0;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        } else {
            throw new IllegalArgumentException();
        }
    }
    public void transferTo(BankAccount other, double amount) {
    if (other == null || other == this || amount <= 0
            || !Double.isFinite(amount) || amount > balance) {
        throw new IllegalArgumentException("Invalid transfer");
    }

    balance = balance - amount;
    other.balance = other.balance + amount;
}
    public double getBalance() {
        return this.balance;
    }
}
