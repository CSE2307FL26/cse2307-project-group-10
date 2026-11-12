package test;

import main.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.jupiter.api.Test;

public class BankAccountTest {
    private BankAccount testAccount;

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

    //Test withdraw functionality
    @Test 
    public void testWithdraw() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        testAccount.withdraw(40);
        assertEquals(60, testAccount.getBalance(), 0.01);
    }

    //Test whether you can withdraw your entire balance
    @Test 
    public void testWithdrawFullBalance() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        testAccount.withdraw(50);
        assertEquals(0, testAccount.getBalance(), 0.01);
    }

    //Test whether you can withdraw a negative balance
    @Test 
    public void testInvalidWithdraw() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        try {
            testAccount.withdraw(-20);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    //Test whether you can withdraw more than is in your account balance
    @Test 
    public void testWithdrawOverdraft() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        try {
            testAccount.withdraw(100);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    //Testing double functionality after PR review
    @Test 
    public void testWithdrawDecimal() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        testAccount.withdraw(12.50);
        assertEquals(87.50, testAccount.getBalance(), 0.01);
    }

    @Test 
    public void testWithdrawDecimalFromDecimalBalance() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(20.75);
        testAccount.withdraw(5.25);
        assertEquals(15.50, testAccount.getBalance(), 0.01);
    }
}
