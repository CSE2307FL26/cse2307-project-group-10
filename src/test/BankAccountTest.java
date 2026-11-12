package test;

import main.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

public class BankAccountTest {

    @Test
    public void testDeposit() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidDeposit() {
        BankAccount testAccount = new BankAccount();
        try {
            testAccount.deposit(-50);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test 
    public void testNewAccountBalanceIsZero() {
        BankAccount testAccount = new BankAccount();
        assertEquals(0, testAccount.getBalance(), 0.01);
    }

    @Test 
    public void testBalanceAfterMultipleDeposits() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(20);
        testAccount.deposit(30);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test 
    public void testSeperateAccountsSeperateBalances() {
        BankAccount account1 = new BankAccount();
        BankAccount account2 = new BankAccount();

        account1.deposit(50);
        account2.deposit(30);
        assertEquals(50, account1.getBalance(), 0.01);
        assertEquals(30, account2.getBalance(), 0.01);
    }
}
