package Bank;

public interface InterestApplicableProduct {

    // for deposits
    //Principal Amount: The initial amount of money deposited into the account.
    //Interest Rate: The annual interest rate provided by the bank as a percentage.
    //Time Period: The duration for which the money is invested, measured in months.
    //for an account it would always be one month

    double getProductPrincipalAmount();
    double getProductRate();//monthly
    int getProductTime();//in months
    int getProductCompoundFrequency();
    void changeInterestRate(double rate);


    boolean isDeposit();//no i to chyba powoduje ze to jest chujowy design xd mozna zmienic w takim razie i nie robic tego tym interfejsem
    //tylko jakos inaczej , moze visitor?
    boolean isLoan();
}