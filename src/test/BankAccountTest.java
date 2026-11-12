package test;

import main.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        }
    }

    @Test
    public void testCollectFee() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        testAccount.collectFee(10);
        assertEquals(40, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testNegativeFee() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        assertThrows(IllegalArgumentException.class, () -> testAccount.collectFee(-10));
    }

    @Test
    public void testFeeExceedsBalance() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(20);
        testAccount.collectFee(30);
        assertEquals(-10, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testApplyInterest() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        testAccount.applyInterest(0.05);
        assertEquals(105, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testNegativeInterest() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        assertThrows(IllegalArgumentException.class, () -> testAccount.applyInterest(-0.05));
        assertEquals(100, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInterestOnZeroBalance() {
        BankAccount testAccount = new BankAccount();
        testAccount.applyInterest(0.05);
        assertEquals(0, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInterestOnNegativeBalance() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(20);
        testAccount.collectFee(30);   // balance is now -10
        assertThrows(IllegalStateException.class, () -> testAccount.applyInterest(0.05));
        assertEquals(-10, testAccount.getBalance(), 0.01);
    }
}
