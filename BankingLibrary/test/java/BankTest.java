import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank(0);
    }

    @AfterEach
    void tearDown() {
        bank = null;
    }

    @Test
    void createCustomer() {
        Bank.LogonData logonData = new Bank.LogonData("username", "password!");
        Bank.Customer customer = bank.new Customer("John", "Smith", LocalDate.of(2000, 1, 1), logonData);

        assertEquals(customer, bank.createCustomer(customer));
    }

    @Test
    void createAccount() {
        Bank.LogonData logonData = new Bank.LogonData("username", "password!");
        Bank.Customer customer = bank.new Customer("John", "Smith", LocalDate.of(2000, 1, 1), logonData);

        bank.createCustomer(customer);
        assertEquals(Bank.CreateAccountStatus.SUCCESS, bank.createAccount(customer.getCustomerId()));
        assertEquals(Bank.CreateAccountStatus.FAIL, bank.createAccount(-1));
    }

    @Test
    void logIn() {
        String username = "username";
        Bank.LogonData logonData = new Bank.LogonData(username, "password!");
        Bank.Customer customer = bank.new Customer("John", "Smith", LocalDate.of(2000, 1, 1), logonData);

        bank.createCustomer(customer);
        assertEquals(Bank.CreateAccountStatus.SUCCESS, bank.createAccount(customer.getCustomerId()));
        assertNotNull(bank.logIn(logonData));
        assertEquals(1, bank.logIn(logonData).size());
        assertEquals(Bank.CreateAccountStatus.SUCCESS, bank.createAccount(customer.getCustomerId()));
        assertEquals(2, bank.logIn(logonData).size());
        assertNull(bank.logIn(new Bank.LogonData(username, "wrongPassword?")));
    }
}