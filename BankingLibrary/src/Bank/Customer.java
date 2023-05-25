package Bank;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Customer {
    private static int nextId = 0;


    Customer(Bank bankRef, final String name, final String surname, final LocalDate dateOfBirth, final LogonData logonData) {
        this.bankRef = bankRef;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.logonData = logonData;
        this.customerId = nextId;
        nextId++;
    }

    private final Bank bankRef;
    private final int customerId;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private LogonData logonData;



    boolean isValid() {
        return !this.name.isBlank() && !this.surname.isBlank() && dateOfBirth.isBefore(LocalDate.now()) && logonData.isValid();
    }

    public String getName() {
        return this.name;
    }
    public String getSurname() {
        return this.surname;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public int getCustomerId() {
        return customerId;
    }

    boolean compareLogonData(LogonData other) {
        return this.logonData.equals(other);
    }

    public List<Account> getAccounts() {
        return bankRef.getCustomerAccounts(this);
    }

    Bank getBankRef() {
        return bankRef;
    }


    public List<Deposit> customerDeposits;
    List<Deposit> getCustomerDeposits(){return customerDeposits;}
    public List<Loan> customerLoans;
    public List<Loan> getCustomerLoans(){return customerLoans;}
    InterestRateStrategy myInterestRateStrategy;

    public void takeLoan( Currency currency,double principalAmount, double rate, int time,int compoundFrequency,
                   InterestRateStrategy concreteInterestRate){
        Loan loan=new Loan(this, currency,principalAmount,rate,time,compoundFrequency,concreteInterestRate);

        if(this.customerLoans==null){
            this.customerLoans=new ArrayList<Loan>();
        }

        this.customerLoans.add(loan);
    };
    public void repayRate(){};
    public void repayLoanFull(){};
    public void makeDeposit( Currency currency,float principalAmount, double rate, int time,int compoundFrequency,
                      InterestRateStrategy concreteInterestRate){
        Deposit E=new Deposit(this,  currency, principalAmount,  time,  compoundFrequency,  rate,concreteInterestRate);
        if(this.customerDeposits==null){
            this.customerDeposits=new ArrayList<Deposit>();
        }

        this.customerDeposits.add(E);
    };
    public void closeDeposit(){};





}