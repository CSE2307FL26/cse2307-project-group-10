package test;

import main.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

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
    public void testDepositCreatesTransactionHistory(){
        BankAccount testAccount = new BankAccount();

        testAccount.deposit(100);  
        assertEquals(1, testAccount.getTransactionHistory().size());
        assertEquals("Deposit: +100.0, Balance: 100.0", testAccount.getTransactionHistory().get(0));
    }

    @Test 
    public void testNewAccountHasNoTransactionHistory(){
        BankAccount testAccount = new BankAccount();
        assertEquals(0, testAccount.getTransactionHistory().size());
    }

    @Test
    public void testTransactionHistoryKeepsOrder(){
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        testAccount.deposit(50);
        assertEquals(2, testAccount.getTransactionHistory().size());
        assertEquals("Deposit: +100.0, Balance: 100.0", testAccount.getTransactionHistory().get(0));
        assertEquals("Deposit: +50.0, Balance: 150.0", testAccount.getTransactionHistory().get(1));
    }

    @Test
    public void testInvalidDepositDoesNotCreateTransactionHistory(){
        BankAccount testAccount = new BankAccount();
        try {
            testAccount.deposit(-50);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
        assertEquals(0, testAccount.getTransactionHistory().size());
    }

    @Test 
    public void testReturnedHistoryDoesNotChangeAccount(){
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        
        testAccount.getTransactionHistory().clear();
        assertEquals(1, testAccount.getTransactionHistory().size());
    }
}
