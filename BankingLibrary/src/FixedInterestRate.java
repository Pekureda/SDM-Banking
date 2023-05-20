public class FixedInterestRate implements InterestRate{
    float previous_rate=0;//mozna cos zrobic zeby wymgal zawsze tej samej stawki procentowej
    @Override
    public Double calculateInterest(InterestApplicableProduct deposit, float percentage) {
        if(deposit.isDeposit()){
            return (double) (deposit.getCurrent_deposit()*percentage);
        }
        else  {



            return for loan;
        } //powinno rzucac error ale errory to na razie jeszcze nie :)
    }
}


