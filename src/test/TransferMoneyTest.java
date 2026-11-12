
package test;

import main.BankAccount;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TransferMoneyTest {

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
}
