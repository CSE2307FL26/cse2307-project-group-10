package main;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final List<BankAccount> accounts = new ArrayList<>();
    private final List<String> adminActivity = new ArrayList<>();

    public void collectFee(BankAccount account, double fee) {
        account.collectFee(fee);
        adminActivity.add(String.format(
                "Charged a $%.2f fee to %s.", fee, account.getAccountName()));
    }

    public void applyInterest(BankAccount account, double rate) {
        double balanceBefore = account.getBalance();
        account.applyInterest(rate);
        double interestPaid = account.getBalance() - balanceBefore;

        adminActivity.add(String.format(
                "Paid $%.2f in interest to %s.", interestPaid, account.getAccountName()));
    }

    public List<String> getAdminActivityLog(){
        return new ArrayList<>(adminActivity);
    }

    public BankAccount createAccount() {
        BankAccount newAccount = new BankAccount();
        newAccount.setAccountName("Account " + (accounts.size() + 1));
        accounts.add(newAccount);
        return newAccount;
    }

    public BankAccount getAccount(int index) {
        return accounts.get(index);
    }

    public int getAccountCount() {
        return accounts.size();
    }
}
