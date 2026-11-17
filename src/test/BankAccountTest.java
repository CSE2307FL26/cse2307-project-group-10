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

     @Test
    public void testTransferMoney() {
        BankAccount first = new BankAccount();
        BankAccount second = new BankAccount();

        first.deposit(100);
        first.transferTo(second, 40);

        assertEquals(60, first.getBalance(), 0.01);
        assertEquals(40, second.getBalance(), 0.01);
    }

    @Test
    public void testNotEnoughMoney() {
        BankAccount first = new BankAccount();
        BankAccount second = new BankAccount();

        first.deposit(20);

        try {
            first.transferTo(second, 50);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(20, first.getBalance(), 0.01);
            assertEquals(0, second.getBalance(), 0.01);
        }
    }

    @Test
    public void testInvalidAmount() {
        BankAccount first = new BankAccount();
        BankAccount second = new BankAccount();

        first.deposit(100);

        try {
            first.transferTo(second, -10);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(100, first.getBalance(), 0.01);
        }
    }

    @Test
    public void testSameAccount() {
        BankAccount first = new BankAccount();
        first.deposit(100);

        try {
            first.transferTo(first, 20);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(100, first.getBalance(), 0.01);
        }
    }

    @Test
    public void testCloseAccount() {
        BankAccount account = new BankAccount();

        account.close();

        assertEquals(true, account.isClosed());
    }

    @Test
    public void testCloseWithMoney() {
        BankAccount account = new BankAccount();
        account.deposit(100);

        try {
            account.close();
            fail();
        } catch (IllegalStateException e) {
            assertEquals(false, account.isClosed());
        }
    }

    @Test
    public void testDepositAfterClose() {
        BankAccount account = new BankAccount();
        account.close();

        try {
            account.deposit(50);
            fail();
        } catch (IllegalStateException e) {
            assertEquals(0, account.getBalance(), 0.01);
        }
    }
    @Test
    public void testDefaultAccountName() {
        BankAccount account = new BankAccount();
        assertEquals("Unnamed Account", account.getAccountName());
    }
    @Test
    public void testRenameAccount() {
        BankAccount account = new BankAccount();

        account.setAccountName("Savings");
        assertEquals("Savings", account.getAccountName());
    }
    @Test
    public void testEmptyAccountName() {
        BankAccount account = new BankAccount();

        assertThrows(IllegalArgumentException.class,
            () -> account.setAccountName(""));
    }
    @Test
    public void testBlankAccountName() {
        BankAccount account = new BankAccount();
        assertThrows(IllegalArgumentException.class,
            () -> account.setAccountName("   "));
        }
    @Test
    public void testRenameDoesNotChangeBalance() {
        BankAccount account = new BankAccount();
        account.deposit(100);
        account.setAccountName("Savings");
        assertEquals(100, account.getBalance(), 0.01);
    }
    @Test
    public void testRenameOnlyOneAccount() {
        BankAccount account1 = new BankAccount();
        BankAccount account2 = new BankAccount();

        account2.setAccountName("Savings");

        assertEquals("Unnamed Account", account1.getAccountName());
        assertEquals("Savings", account2.getAccountName());
    }
}
