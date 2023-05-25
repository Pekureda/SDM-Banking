package Bank;

import java.util.Currency;

public class Deposit extends Account implements InterestApplicableProduct{
    protected final float principalAmount;
    public float current_deposit=0;
    public float deposit_total_money_time;
    public double principal;
    public int time;
    public int compoundFrequency;
    public boolean isDeposit=true;

    public int timeLeft;

    Deposit(Customer owner, Currency currency,float current_deposit, int time, int compoundFrequency, double rate,InterestRateStrategy concreteInterestRate){
        super( owner,  current_deposit,  currency);
        super.myInterestRateStrategy=concreteInterestRate;
        this.current_deposit=current_deposit;
        this.time=time;//months
        this.compoundFrequency=compoundFrequency;

        this.principalAmount=this.current_deposit;
        super.interestRate=rate;

    }



    @Override
    public double getProductPrincipalAmount(){
        return principalAmount;
    }

    @Override
    public double getProductRate(){
        return interestRate;
    }

    @Override
    public int getProductTime(){
        return time;
    }

    @Override
    public int getProductCompoundFrequency(){
        return compoundFrequency;
    }

    @Override
    public void setProductCompoundFrequency(int compoundFrequency){
         this.compoundFrequency=compoundFrequency;
    }

    @Override
    public void setInterestRate(double rate){
    interestRate=rate;
    }

    @Override
    public boolean isDeposit(){
        return true;

    }

    @Override
    public Double calculateInterest(){
        return  myInterestRateStrategy.calculateInterest(this);
    }

    @Override
    public boolean isLoan(){
        return false;
    }
    public double finishDepositEarly(double penalty){

        double temp=this.current_deposit-penalty;

        //todo destroy this object;
    return temp;
    }

    @Override
    public void setInterestRateStrategy(InterestRateStrategy concreteStrategy){
        this.myInterestRateStrategy=concreteStrategy;
    }

    int getTimeLeft(){
        return this.timeLeft;
    }
    void setTimeLeft(int timeleft){
        this.timeLeft=timeleft;
    }
}
