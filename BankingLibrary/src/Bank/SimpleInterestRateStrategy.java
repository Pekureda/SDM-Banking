package Bank;

public class SimpleInterestRateStrategy implements InterestRateStrategy {


    @Override
    public Double calculateInterest(InterestApplicableProduct product) {
        if (product.isDeposit()) {
            return (double) (product.getProductPrincipalAmount() * product.getProductRate()*product.getProductTime());
        } else if(product.isLoan()) {

            return (double) (product.getProductPrincipalAmount() * product.getProductRate()*product.getProductTime());


            //return for loan
        }
        else{
            return (double) (product.getProductPrincipalAmount() * product.getProductRate()*product.getProductTime());

        }
    }
}



