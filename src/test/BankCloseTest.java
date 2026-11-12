
package test;

import main.BankAccount;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.jupiter.api.Test;

public class BankCloseTest {

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
}
