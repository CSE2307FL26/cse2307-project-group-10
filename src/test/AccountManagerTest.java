package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.AccountManager;
import main.BankAccount;


public class AccountManagerTest {

    private AccountManager manager;

    @Before
    public void setUp(){
        manager = new AccountManager();
    }

    @Test 
    public void testCreateAccount(){
        BankAccount account = manager.createAccount();
        assertEquals(1, manager.getAccountCount());
        assertEquals(0, account.getBalance(), 0.01);
    }

    @Test
    public void testAccountHaveSeparateBalances(){

        BankAccount account1 = manager.createAccount();
        BankAccount account2 = manager.createAccount();

        account1.deposit(100);
        account2.deposit(200);

        assertEquals(2, manager.getAccountCount());
        assertEquals(100, account1.getBalance(), 0.01);
        assertEquals(200, account2.getBalance(), 0.01);
    }

    @Test
    public void testDepositIntoSelectedAccount(){
        manager.createAccount();
        manager.createAccount();

        BankAccount selected = manager.getAccount(0);
        selected.deposit(100);

        selected = manager.getAccount(1);
        selected.deposit(50);

        selected = manager.getAccount(0);
        selected.deposit(20);

        assertEquals(120, manager.getAccount(0).getBalance(), 0.01);
        assertEquals(50, manager.getAccount(1).getBalance(), 0.01);
    }
}
