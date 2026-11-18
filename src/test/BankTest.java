package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

    //Testing typed input is converted to a number
    @Test
    public void testParseWholeNumber(){
        assertEquals(50, Bank.parseAmount("50"), 0.01);
    }

    @Test
    public void testParseDouble(){
        assertEquals(12.50, Bank.parseAmount("12.50"), 0.01);
    }
    
    @Test
    public void testParseNumberWithSpaces(){
        assertEquals(20, Bank.parseAmount("  20  "), 0.01);
    }

    @Test
    public void testParseLettersAreRejected(){
        try {
            Bank.parseAmount("abc");
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testParseEmptyInputIsRejected(){
        try {
            Bank.parseAmount("");
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    //Testing menu selections are checked against the menu size
    @Test
    public void testParseSelectionInRange(){
        assertEquals(3, Bank.parseSelection("3", 5));
    }

    @Test
    public void testParseSelectionWithSpaces(){
        assertEquals(2, Bank.parseSelection("  2  ", 5));
    }

    @Test
    public void testParseSelectionEdges(){
        assertEquals(1, Bank.parseSelection("1", 5));
        assertEquals(5, Bank.parseSelection("5", 5));
    }

    @Test
    public void testParseSelectionTooLowIsRejected(){
        try {
            Bank.parseSelection("0", 5);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testParseSelectionTooHighIsRejected(){
        try {
            Bank.parseSelection("6", 5);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testParseSelectionLettersAreRejected(){
        try {
            Bank.parseSelection("abc", 5);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testParseSelectionDecimalIsRejected(){
        try {
            Bank.parseSelection("2.5", 5);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
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
