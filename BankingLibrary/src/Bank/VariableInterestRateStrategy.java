package Bank;

public class VariableInterestRateStrategy implements InterestRateStrategy {


    @Override
    public Double calculateInterest(InterestApplicableProduct deposit) {
        if (deposit.isDeposit()) {
            return (double) (deposit.getProductPrincipalAmount() *deposit.getProductRate());
        } else {




            //return for loan
            return 0.0;
        } //powinno rzucac error ale errory to na razie jeszcze nie :)
    }


}
