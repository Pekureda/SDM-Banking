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

import Bank.Bank;
import Bank.InterbankPaymentSystem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Currency;

public class LoanTest {




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
        void takeLoan() {
            Account accountA = bankA.logIn(logonDataA1).get(0);
            Customer cuz=bankA.getCustomerMap().get("johnny");
            InterestRateStrategy concreteStrategy=new CompoundStrategyInterest();
            cuz.takeLoan(Currency.getInstance("PLN"),1000,0.04,10,1,concreteStrategy);
/*
            Account accountA1 = bankA.logIn(logonDataA1);//nie ma sposobu jak rozpoznac czy cos jest pozyczką
            if(accountA1 instanceof  Loan){
                 System.out.println("udalo sie jestem pozyczka");
             }
             Loan loan=(Loan) accountA1;////////tu nie dziala
*/
            Loan loan= cuz.getCustomerLoans().get(0);
            double howMuchIsInterest=loan.calculateInterest();
            System.out.println(howMuchIsInterest);



        }







}
