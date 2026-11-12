package main;

import java.util.ArrayList;
import java.util.List;

public class AccountManager {
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
}


