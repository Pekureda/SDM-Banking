import Bank.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Customer cuz=bankA.getCustomerMap().get("johnny");
        InterestRateStrategy concreteStrategy1=new CompoundStrategyInterest();
        cuz.takeLoan(Currency.getInstance("PLN"),1000,0.20,10,12,concreteStrategy1);



        Loan loan= cuz.getCustomerLoans().get(0);
        double howMuchIsInterest=loan.calculateInterest();
        System.out.println(howMuchIsInterest);
        loan.setAmountToRepay(loan.getProductPrincipalAmount()+loan.calculateInterest());
        //System.out.println(loan.getAmountToRepay());

        InterestRateStrategy concreteStrategy2=new SimpleInterestRateStrategy();
        loan.setInterestRateStrategy(concreteStrategy2);

        howMuchIsInterest=loan.calculateInterest();
        System.out.println(howMuchIsInterest);
        //InterestRateStrategy concreteStrategy3=new CompoundStrategyInterest();
        //InterestRateStrategy concreteStrategy4=new CompoundStrategyInterest();
        //InterestRateStrategy concreteStrategy5=new CompoundStrategyInterest();
        loan.changeInterestRate(0.02);
        loan.setProductCompoundFrequency(1);
        loan.setProductTime(1);
        loan.setProductPrincipalAmount(1000);
        loan.setAmountToRepay(1000);
        loan.setInterestRateStrategy(concreteStrategy1);

        //System.out.println(loan.calculateInterest());
        assertEquals(20, loan.calculateInterest());

        loan.setInterestRateStrategy(concreteStrategy2);

        //System.out.println(loan.calculateInterest());
        assertEquals(20, loan.calculateInterest());

        System.out.println(loan.getBalance());
        loan.setAmountToRepay(1000.001);

        loan.repayLoan(1000);

        //System.out.println("splacanie");
        assertEquals(0.0, loan.getBalance());
        assertEquals(0.0, loan.getAmountToRepay());

    }
}