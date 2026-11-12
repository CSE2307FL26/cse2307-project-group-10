package main;

public class BankAccount {

    private double balance;
    private boolean closed;

    public BankAccount() {
        this.balance = 0;
        this.closed = false;
    }

    public void deposit(double amount) {
        if (closed) {
            throw new IllegalStateException("Account is closed");
        }
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

    public void close() {
        if (closed) {
            throw new IllegalStateException("Account already closed");
        }
        if (balance != 0) {
            throw new IllegalStateException("Account still has money");
        }
        closed = true;
    }

    public boolean isClosed() {
        return closed;
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
