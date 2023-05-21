package Bank;

public class FixedInterestRate implements InterestRate{
    float previous_rate=0;//mozna cos zrobic zeby wymgal zawsze tej samej stawki procentowej
    @Override
    public Double calculateInterest(InterestApplicableProduct deposit, float percentage) {
        if(deposit.isDeposit()){
            return (double) (deposit.getProductPrincipalAmount()*percentage);
        }
        else  {



            //return for loan;
            return 0.0;
        } //powinno rzucac error ale errory to na razie jeszcze nie :)
    }
}


