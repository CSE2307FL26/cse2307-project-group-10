package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.Bank;
import main.BankAccount;


public class BankTest {

    private Bank manager;

    @Before
    public void setUp(){
        manager = new Bank();
    }

    @Test
    public void testAdminLogOrder(){
        BankAccount account = manager.createAccount();
        account.deposit(105);

        manager.collectFee(account, 5);
        manager.applyInterest(account, 0.05);

        assertEquals(2, manager.getAdminActivityLog().size());
        assertEquals("Charged a $5.00 fee to Account 1.", manager.getAdminActivityLog().get(0));
        assertEquals("Paid $5.00 in interest to Account 1.", manager.getAdminActivityLog().get(1));
    }

    @Test
    public void testNewSessionStartsWithEmptyAdminLog(){
       BankAccount account = manager.createAccount();
       account.deposit(20);
       manager.collectFee(account, 5);
       assertEquals(1, manager.getAdminActivityLog().size());

       Bank newSession = new Bank();
       assertEquals(0, newSession.getAdminActivityLog().size());
    }

    @Test
    public void testInvalidFeeDoesNotCreateLogEntry(){
        BankAccount account = manager.createAccount();
        boolean feeRejected = false;
        try{
            manager.collectFee(account, -5);
        } catch (IllegalArgumentException expected){
            feeRejected = true;
        }
        assertEquals(true, feeRejected);
        assertEquals(0, manager.getAdminActivityLog().size());
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
    @Test
    public void testNewAccountsGetNumberedNames(){
        manager.createAccount();
        manager.createAccount();
        assertEquals("Account 1", manager.getAccount(0).getAccountName());
        assertEquals("Account 2", manager.getAccount(1).getAccountName());
    }

    @Test
    public void testRenamedAccountKeepsNameAfterTransfer(){
        manager.createAccount();
        manager.createAccount();
        manager.getAccount(1).setAccountName("Savings");
        manager.getAccount(0).deposit(100);
        manager.getAccount(0).transferTo(manager.getAccount(1), 40);
        assertEquals("Savings", manager.getAccount(1).getAccountName());
        assertEquals("Account 1", manager.getAccount(0).getAccountName());
        assertEquals(40, manager.getAccount(1).getBalance(), 0.01);
    }
}
