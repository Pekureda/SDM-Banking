package Bank;

public interface InterestApplicableProduct {

    // for deposits
    //Principal Amount: The initial amount of money deposited into the account.
    //Interest Rate: The annual interest rate provided by the bank as a percentage.
    //Time Period: The duration for which the money is invested, measured in months.
    //for an account it would always be one month

    double getProductPrincipalAmount();
    void setProductPrincipalAmount(double newPrincipalAmount);
    double getProductRate();//monthly
    void changeInterestRate(double rate);
    int getProductTime();//in months
    void setProductTime(int time);
    int getProductCompoundFrequency();
    void setProductCompoundFrequency(int compoundFrequency);


    Double calculateInterest();
    void setInterestRateStrategy(InterestRateStrategy concreteStrategy);




    boolean isDeposit();
    boolean isLoan();
}
