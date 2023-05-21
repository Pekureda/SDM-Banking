public class SimpleInterestRate implements InterestRate {


    @Override
    public Double calculateInterest(InterestApplicableProduct deposit, float percentage) {
        if (deposit.isDeposit()) {
            return (double) (deposit.getCurrent_deposit() * percentage*deposit.getDeposit_total_money_time());
        } else {



            //return for loan
            return 0.0;
        } //powinno rzucac error ale errory to na razie jeszcze nie :)
    }
}



