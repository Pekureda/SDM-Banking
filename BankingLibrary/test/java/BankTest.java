import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

import bank.*;

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
        String customerName = "John";
        String customerSurname = "Smith";
        LocalDate customerDateOfBirth = LocalDate.of(2000, Month.JANUARY, 1);
        LogonData logonData = new LogonData("username", "password!");
        Customer customer = bank.registerCustomer(customerName, customerSurname, customerDateOfBirth, logonData);

        assertNotEquals(customer, null);
        assertEquals(customer.getName(), customerName);
        assertEquals(customer.getSurname(), customerSurname);
        assertEquals(customer.getDateOfBirth(), customerDateOfBirth);
        // Check logon data?
    }

    @Test
    void createAccount() {
        String customerName = "John";
        String customerSurname = "Smith";
        LocalDate customerDateOfBirth = LocalDate.of(2000, Month.JANUARY, 1);
        LogonData logonData = new LogonData("username", "password!");
        Customer customer = bank.registerCustomer(customerName, customerSurname, customerDateOfBirth, logonData);

        assertNotEquals(customer, null);
        assertEquals(Bank.CreateAccountStatus.SUCCESS, bank.openAccount(customer.getCustomerId()));
        assertEquals(Bank.CreateAccountStatus.FAIL, bank.openAccount(-1));
    }

    @Test
    void logIn() {
        String customerName = "John";
        String customerSurname = "Smith";
        LocalDate customerDateOfBirth = LocalDate.of(2000, Month.JANUARY, 1);
        LogonData logonData = new LogonData("username", "password!");
        Customer customer = bank.registerCustomer(customerName, customerSurname, customerDateOfBirth, logonData);

        assertEquals(Bank.CreateAccountStatus.SUCCESS, bank.openAccount(customer.getCustomerId()));
        assertNotNull(bank.logIn(logonData));
        assertEquals(1, bank.logIn(logonData).size());
        assertEquals(Bank.CreateAccountStatus.SUCCESS, bank.openAccount(customer.getCustomerId()));
        assertEquals(2, bank.logIn(logonData).size());
        assertNotEquals(logonData, new LogonData("username", "wrongPassword?"));
        assertNull(bank.logIn(new LogonData("username", "wrongPassword?")));
    }
}