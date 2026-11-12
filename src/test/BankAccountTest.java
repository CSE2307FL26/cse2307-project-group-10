package test;

import main.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
}
