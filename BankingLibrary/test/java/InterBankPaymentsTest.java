import Bank.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InterBankPaymentsTest {
    InterbankPaymentSystem interbankPaymentSystem;
    Bank bankA;
    Bank bankB;

    LogonData logonDataA1;
    String accountIdA1;
    LogonData logonDataB1;
    String accountIdB1;

    @BeforeEach
    void setUp() {
        interbankPaymentSystem = new InterbankPaymentSystem();

        bankA = new Bank("0000");
        bankB = new Bank("0001");

        assertTrue(interbankPaymentSystem.registerBank(bankA));
        assertTrue(interbankPaymentSystem.registerBank(bankB));

        logonDataA1 = new LogonData("johnny", "lovesSmithing");
        logonDataB1 = new LogonData("bob", "theBuilder");

        bankA.registerCustomer("John", "Smith", LocalDate.of(2000, Month.JANUARY, 1), logonDataA1);
        bankB.registerCustomer("Bob", "Larry", LocalDate.of(2000, Month.MARCH, 1), logonDataB1);

        bankA.openAccount(logonDataA1, 1000.0, Currency.getInstance("PLN"));
        bankB.openAccount(logonDataB1,  0.0, Currency.getInstance("PLN"));

        accountIdA1 = bankA.logIn(logonDataA1).get(0).getId();
        accountIdB1 = bankB.logIn(logonDataB1).get(0).getId();
    }

    @AfterEach
    void tearDown() {
        bankA = null;
        bankB = null;
        interbankPaymentSystem = null;
    }

    @Test
    void externalTransferTest() {
        Account accountA = bankA.logIn(logonDataA1).get(0);
        assertTrue(accountA.orderTransfer(500, Currency.getInstance("PLN"), accountIdB1, "Payment for new workshop"));
        assertEquals(500, accountA.getBalance());

        Account accountB = bankB.logIn(logonDataB1).get(0);
        assertEquals(0, accountB.getBalance());

        interbankPaymentSystem.batchTransfer(bankB.bankCode);
        assertEquals(500, accountB.getBalance());

    }
}
