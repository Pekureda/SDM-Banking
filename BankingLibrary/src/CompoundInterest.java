public class CompoundInterest implements InterestRate {


    @Override
    public Double calculateInterest(InterestApplicableProduct deposit, float percentage) {
        if (deposit.isDeposit()) {
            return calculateCompoundInterest(deposit.getProductPrincipal(),
                    deposit.getProductRate(), deposit.getProductTime(), deposit.getProductCompoundFrequency());
        } else {



            


            //return for loan;
            return 0.0;
        } //powinno rzucac error ale errory to na razie jeszcze nie :)
    }

    public static double calculateCompoundInterest(double principal, double rate, int time, int compoundFrequency) {
        double interestRate = rate / 100.0; // Convert rate to decimal
        double amount = principal * Math.pow(1 + interestRate / compoundFrequency, compoundFrequency * time);
        double compoundInterest = amount - principal;
        return compoundInterest;
    }
}
