import Bank.Commands.IngoingTransferCommand;
import Bank.InterestRate.SimpleAccountInterestRateStrategy;
import Bank.InterestRate.SimpleDepositInterestRateStrategy;
import Bank.InterestRate.SimpleLoanInterestRateStrategy;
import Bank.Reporting.CustomersWithNameVisitor;
import Bank.Reporting.OnlyOutgoingTransactionsVisitor;
import Bank.Reporting.OverNAccountsReport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Bank.*;
import Bank.Commands.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {
    Bank bank;
    Bank otherBank;
    InterbankPaymentSystem interbankPaymentSystem;

    @BeforeEach
    void setUp() {
        bank = new Bank("0123");
        otherBank = new Bank("3210");
        interbankPaymentSystem = new InterbankPaymentSystem();
    }

    @AfterEach
    void tearDown() {
        bank = null;
        otherBank = null;
        interbankPaymentSystem = null;
    }

    @Test
    void basicCreateCustomer() {
        String customerName = "John";
        String customerSurname = "Smith";
        String customerUsername = "majkel1";
        Customer customer = bank.createCustomer(customerUsername, customerName, customerSurname);

        assertNotNull(customer);
        assertEquals(customer.getName(), customerName);
        assertEquals(customer.getSurname(), customerSurname);
        assertEquals(customer.getUsername(), customerUsername);

        String otherCustomerName = "Bob";
        String otherCustomerSurname = "Anvil";
        String otherCustomerUsername = "hoshiFan";

        assertNull(bank.createCustomer(customerUsername, otherCustomerName, otherCustomerSurname));

        Customer otherCustomer = bank.createCustomer(otherCustomerUsername, otherCustomerName, otherCustomerSurname);

        assertNotNull(otherCustomer);
        assertEquals(otherCustomer.getName(), otherCustomerName);
        assertEquals(otherCustomer.getSurname(), otherCustomerSurname);
        assertEquals(otherCustomer.getUsername(), otherCustomerUsername);

        assertNotEquals(customer, otherCustomer);
        assertEquals(bank.getCustomer(customerUsername), customer);
        assertEquals(bank.getCustomer(otherCustomerUsername), otherCustomer);
    }

    @Test
    void createAccount() {
        String customerName = "John";
        String customerSurname = "Smith";
        String customerUsername = "majkel1";

        String otherCustomerName = "Bob";
        String otherCustomerSurname = "Anvil";
        String otherCustomerUsername = "hoshiFan";
        Customer customer = bank.createCustomer(customerUsername, customerName, customerSurname);
        Customer otherCustomer = bank.createCustomer(otherCustomerUsername, otherCustomerName, otherCustomerSurname);

        assertNotNull(customer);
        assertNotNull(otherCustomer);
        assertNotEquals(customer, otherCustomer);

        for (int i = 1; i < 10; i++) {
            Account customerAccount = bank.createAccount(customer, new SimpleAccountInterestRateStrategy(1));
            Account otherCustomerAccount = bank.createAccount(otherCustomer, new SimpleAccountInterestRateStrategy(1));

            assertNotEquals(customerAccount, otherCustomerAccount);
            assertEquals(bank.getCustomerAccounts(customer).size(), i);
            assertEquals(bank.getCustomerAccounts(otherCustomer).size(), i);

            assertNotNull(bank.getAccountByNumber(customerAccount.accountNumber));
            assertNotNull(bank.getAccountByNumber(otherCustomerAccount.accountNumber));

            assertEquals(customerAccount, bank.getAccountByNumber(customerAccount.accountNumber));
            assertEquals(otherCustomerAccount, bank.getAccountByNumber(otherCustomerAccount.accountNumber));
        }
    }

    @Test
    void internalTransaction() {
        String customerName = "John";
        String customerSurname = "Smith";
        String customerUsername = "majkel1";

        String otherCustomerName = "Bob";
        String otherCustomerSurname = "Anvil";
        String otherCustomerUsername = "hoshiFan";

        Customer customer = bank.createCustomer(customerUsername, customerName, customerSurname);
        Customer otherCustomer = bank.createCustomer(otherCustomerUsername, otherCustomerName, otherCustomerSurname);

        Account customerAccount = bank.createAccount(customer, new SimpleAccountInterestRateStrategy(1));
        var customerAccountNumber = customerAccount.accountNumber;
        assertEquals(bank.bankCode + String.format("%06d", 0), customerAccountNumber.toString());
        Account otherCustomerAccount = bank.createAccount(otherCustomer, new SimpleAccountInterestRateStrategy(1));
        var otherCustomerAccountNumber = otherCustomerAccount.accountNumber;
        assertEquals(bank.bankCode + String.format("%06d", 1), otherCustomerAccountNumber.toString());

        var customerAccounts = bank.getCustomerAccounts(customer);
        assertEquals(1, customerAccounts.size());
        assertEquals(customerAccounts.get(0), customerAccount);

        var otherCustomerAccounts = bank.getCustomerAccounts(otherCustomer);
        assertEquals(1, otherCustomerAccounts.size());
        assertEquals(otherCustomerAccounts.get(0), otherCustomerAccount);

        double amountPaid = 500;
        assertTrue(bank.payment(customerAccount, amountPaid));
        assertEquals(customerAccount.getBalance(), amountPaid);

        assertTrue(bank.transfer(customerAccount, otherCustomerAccountNumber, 250, "Too much"));
        assertEquals(250, otherCustomerAccount.getBalance());
        assertEquals(250, customerAccount.getBalance());
        assertTrue(bank.transfer(otherCustomerAccount, customerAccountNumber, 100, "Tip"));
        assertEquals(150, otherCustomerAccount.getBalance());
        assertEquals(350, customerAccount.getBalance());
        assertFalse(bank.transfer(otherCustomerAccount, new AccountNumber(bank.bankCode, "123456"), 1, "Please fail"));
        //assertFalse(bank.transfer(otherCustomerAccount, new AccountNumber("1111", "123456"), 1, "Please fail"));

        assertTrue(bank.withdraw(customerAccount, 200));
        assertEquals(150, customerAccount.getBalance());

        assertFalse(bank.withdraw(otherCustomerAccount, 1000));
        assertEquals(150, otherCustomerAccount.getBalance());

        otherBank.withdraw(otherCustomerAccount, 150);
        assertEquals(0, otherCustomerAccount.getBalance());
    }

    @Test
    void ExternalTransaction() {
        String customerName = "John";
        String customerSurname = "Smith";
        String customerUsername = "majkel1";

        String otherCustomerName = "Bob";
        String otherCustomerSurname = "Anvil";
        String otherCustomerUsername = "hoshiFan";

        // Create customers
        Customer customer = bank.createCustomer(customerUsername, customerName, customerSurname);
        Customer otherCustomer = otherBank.createCustomer(otherCustomerUsername, otherCustomerName, otherCustomerSurname);

        // Create accounts
        Account customerAccount = bank.createAccount(customer, new SimpleAccountInterestRateStrategy(1));
        var customerAccountNumber = customerAccount.accountNumber;
        assertEquals(bank.bankCode + String.format("%06d", 0), customerAccountNumber.toString());
        Account otherCustomerAccount = otherBank.createAccount(otherCustomer, new SimpleAccountInterestRateStrategy(1));
        var otherCustomerAccountNumber = otherCustomerAccount.accountNumber;
        assertEquals(otherBank.bankCode + String.format("%06d", 0), otherCustomerAccountNumber.toString());

        // Check if the account was created customer
        var customerAccounts = bank.getCustomerAccounts(customer);
        assertEquals(1, customerAccounts.size());
        assertEquals(customerAccounts.get(0), customerAccount);

        // Check if the account was created for otherCustomer
        var otherCustomerAccounts = otherBank.getCustomerAccounts(otherCustomer);
        assertEquals(1, otherCustomerAccounts.size());
        assertEquals(otherCustomerAccounts.get(0), otherCustomerAccount);

        // Top up customer account
        double amountPaid = 500;
        assertTrue(bank.payment(customerAccount, amountPaid));
        assertEquals(customerAccount.getBalance(), amountPaid);

        // Register banks
        interbankPaymentSystem.registerBank(bank);
        interbankPaymentSystem.registerBank(otherBank);

        // Transfer money
        assertTrue(bank.transfer(customerAccount, otherCustomerAccountNumber, 250, "Too much"));
        assertEquals(0, otherCustomerAccount.getBalance());

        // Receive money from ibpa
        interbankPaymentSystem.receiveTransfers(otherBank);
        assertEquals(250, otherCustomerAccount.getBalance());
        assertEquals(250, customerAccount.getBalance());

        // Transfer money back
        assertTrue(otherBank.transfer(otherCustomerAccount, customerAccountNumber, 100, "Tip"));
        assertEquals(150, otherCustomerAccount.getBalance());
        assertEquals(250, customerAccount.getBalance());

        // Receive money from ibpa
        interbankPaymentSystem.receiveTransfers(bank);
        assertEquals(350, customerAccount.getBalance());

        // Try transfer money to other bank but the bank does not exist
        // This should pass, because ibpa will create a returning transfer to receive by bank
        assertTrue(bank.transfer(customerAccount, new AccountNumber("0000000000"), 1, "Please fail"));
        assertEquals(349, customerAccount.getBalance());
        interbankPaymentSystem.receiveTransfers(bank);
        assertEquals(350, customerAccount.getBalance());

        // Try transfer money to other bank but the account at that bank does not exist
        assertTrue(bank.transfer(customerAccount, new AccountNumber(otherBank.bankCode, "123456"), 1, "Please fail"));
        assertEquals(349, customerAccount.getBalance());
        interbankPaymentSystem.receiveTransfers(bank);
        assertEquals(349, customerAccount.getBalance());

        interbankPaymentSystem.receiveTransfers(otherBank);
        assertEquals(349, customerAccount.getBalance());
        interbankPaymentSystem.receiveTransfers(bank);
        assertEquals(350, customerAccount.getBalance());
    }

    @Test
    void Loans() {
        String customerName = "John";
        String customerSurname = "Smith";
        String customerUsername = "majkel1";

        Customer customer = bank.createCustomer(customerUsername, customerName, customerSurname);
        Account customerAccount = bank.createAccount(customer, new SimpleAccountInterestRateStrategy(1));
        var customerAccountNumber = customerAccount.accountNumber;
        assertEquals(bank.bankCode + String.format("%06d", 0), customerAccountNumber.toString());

        double amountPaid = 500;
        assertTrue(bank.payment(customerAccount, amountPaid));
        assertEquals(customerAccount.getBalance(), amountPaid);

        assertTrue(bank.createLoan(customerAccount, 1000, new SimpleLoanInterestRateStrategy(10)));
        assertEquals(1500, customerAccount.getBalance());
        Loan customerLoan = customerAccount.getLoans().get(0);

        // Repay loan
        bank.repayLoan(customerAccount, customerLoan, 500);
        assertEquals(-500, customerLoan.getBalance());
        assertEquals(1000, customerAccount.getBalance());

        assertTrue(customerLoan.applyInterest());
        assertEquals(-550, customerLoan.getBalance());
        assertFalse(bank.repayLoan(customerLoan, customerLoan, 550)); // Wrong arguments
        assertFalse(bank.repayLoan(customerAccount, customerLoan, 551)); // Overpayment
        assertTrue(bank.repayLoan(customerAccount, customerLoan, 550)); // Correct payment

        assertEquals(0, customerLoan.getBalance());
    }

    @Test
    void Deposits() {
        String customerName = "John";
        String customerSurname = "Smith";
        String customerUsername = "majkel1";

        Customer customer = bank.createCustomer(customerUsername, customerName, customerSurname);
        Account customerAccount = bank.createAccount(customer, new SimpleAccountInterestRateStrategy(1));
        var customerAccountNumber = customerAccount.accountNumber;
        assertEquals(bank.bankCode + String.format("%06d", 0), customerAccountNumber.toString());

        double amountPaid = 500;
        assertTrue(bank.payment(customerAccount, amountPaid));
        assertEquals(customerAccount.getBalance(), amountPaid);

        assertFalse(bank.createDeposit(customerAccount, 1000, new SimpleDepositInterestRateStrategy(10, LocalDate.now().minusDays(1))));
        assertTrue(bank.createDeposit(customerAccount, 100, new SimpleDepositInterestRateStrategy(10, LocalDate.now().minusDays(1))));
        assertTrue(bank.createDeposit(customerAccount, 100, new SimpleDepositInterestRateStrategy(10, LocalDate.now().plusDays(2))));
        assertEquals(300, customerAccount.getBalance());
        Deposit customerDepositDue = customerAccount.getDeposits().get(0);
        Deposit customerDepositNotDue = customerAccount.getDeposits().get(1);

        // Apply interest rate to deposit
        customerDepositDue.applyInterest();
        assertTrue(customerDepositDue.isDue());
        assertEquals(110, customerDepositDue.getBalance());
        assertEquals(100, customerDepositDue.getStartingBalance());

        customerDepositNotDue.applyInterest();
        assertFalse(customerDepositNotDue.isDue());
        assertEquals(110, customerDepositNotDue.getBalance());
        assertEquals(100, customerDepositNotDue.getStartingBalance());

        // Close due deposit
        bank.closeDeposit(customerDepositDue);
        assertEquals(410, customerAccount.getBalance());

        // Close not due deposit
        bank.closeDeposit(customerDepositNotDue);
        assertEquals(510, customerAccount.getBalance());
    }

    @Test
    void Reporting() {
        internalTransaction();

        var customerReport = bank.doCustomerReport(new CustomersWithNameVisitor("Bob"));
        var accountReport = bank.doAccountReport(new OverNAccountsReport(100));
        var operationReport = bank.doOperationReport(bank, new OnlyOutgoingTransactionsVisitor());

        assertEquals(1, customerReport.size());
        assertEquals(1, accountReport.size());
        assertTrue(operationReport.size() > 0);
    }

}