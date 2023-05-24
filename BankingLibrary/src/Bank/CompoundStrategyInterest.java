package Bank;

public class CompoundStrategyInterest implements InterestRateStrategy {


    @Override
    public Double calculateInterest(InterestApplicableProduct product) {
        if (product.isDeposit()) {
            return calculateCompoundInterest(product.getProductPrincipalAmount(),
                    product.getProductRate(), product.getProductTime(), product.getProductCompoundFrequency());
        } else if (product.isLoan()) {
            return calculateCompoundInterest(product.getProductPrincipalAmount(),
                    product.getProductRate(), product.getProductTime(), product.getProductCompoundFrequency());
        }
        else
        {
            return calculateCompoundInterest(product.getProductPrincipalAmount(),
                    product.getProductRate(), product.getProductTime(), product.getProductCompoundFrequency());

        }
    }

    public static double calculateCompoundInterest(double principal, double rate, int time, int compoundFrequency) {
        double interestRate = rate / 100.0; // Convert rate to decimal
        double amount = principal * Math.pow(1 + interestRate / compoundFrequency, compoundFrequency * time);
        double compoundInterest = amount - principal;
        return compoundInterest;
    }
}
