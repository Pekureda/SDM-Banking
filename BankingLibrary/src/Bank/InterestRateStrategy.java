package Bank;

public interface InterestRateStrategy {

    Double calculateInterest(InterestApplicableProduct deposit);//different times of capitalization?
    //no for now just calculate interest each month
}
