import Bank.Account;
import Bank.Bank;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import Bank.Account;
import Bank.Bank;
import Bank.Customer;
import Bank.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Currency;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
public class DepositTest {

    InterbankPaymentSystem interbankPaymentSystem;
    Bank bankA;

    LogonData logonDataA1;
    String accountIdA1;


        Account account;

        @BeforeEach
        void setUp() {
             this.bankA = new Bank("0001");
            LogonData logonData = new LogonData("username", "password!");


            this.logonDataA1 = new LogonData("johnny", "lovesSmithing");

            bankA.registerCustomer("John", "Smith", LocalDate.of(2000, Month.JANUARY, 1), logonDataA1);

            bankA.openAccount(logonDataA1, 1000.0, Currency.getInstance("PLN"));

        }

        @AfterEach
        void tearDown() {
            //bankA=null;

            //account = null;
        }

        @Test
        void makeDeposit() {
            Account accountA = bankA.logIn(logonDataA1).get(0);
            Customer cuz=bankA.getCustomerMap().get("johnny");
            InterestRateStrategy concreteStrategy=new CompoundStrategyInterest();
            cuz.makeDeposit(Currency.getInstance("PLN"),1000,0.20,10,
                    1,concreteStrategy);
/*

*/
            Deposit deposit= cuz.customerDeposits.get(0);
            double currentInterest=deposit.calculateInterest();
            System.out.println(currentInterest);



        }




}
