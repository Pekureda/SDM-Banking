import Bank.Account;
import Bank.Bank;
import Bank.Customer;
import Bank.LogonData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    Account account;

    @BeforeEach
    void setUp() {
        Bank bank = new Bank("0123");
        String customerName = "John";
        String customerSurname = "Smith";
        LocalDate customerDateOfBirth = LocalDate.of(2000, Month.JANUARY, 1);
        LogonData logonData = new LogonData("username", "password!");
        Customer customer = bank.registerCustomer(customerName, customerSurname, customerDateOfBirth, logonData);
        bank.openAccount(customer.getCustomerId(), logonData);
        account = bank.getCustomerAccounts(customer).get(0);
    }

    @AfterEach
    void tearDown() {
        account = null;
    }

    @Test
    void getBalance() {
        assertEquals(0.0, account.getBalance(), 0.01);
    }

    @Test
    void updateBalance() throws Exception{
        account.updateBalance(0.3);
        assertEquals(0.3, account.getBalance(), 0.01);
        account.updateBalance(0.3);
        assertEquals(0.6, account.getBalance(), 0.01);
        account.updateBalance(0.3);
        assertEquals(0.9, account.getBalance(), 0.01);
        account.updateBalance(-0.3);
        assertEquals(0.6, account.getBalance(), 0.01);
        assertThrows(Exception.class, () -> account.updateBalance(-1));
    }
}
