package Bank;

import java.util.Currency;

public class Deposit extends Account implements InterestApplicableProduct{
    private final float principalAmount;
    public float current_deposit=0;
    public float deposit_total_money_time;
    public double principal;
    public double rate;
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
        this.interestRate=rate;

    }



    @Override
    public double getProductPrincipalAmount() {
        return principalAmount;
    }

    @Override
    public double getProductRate() {
        return rate;
    }

    @Override
    public int getProductTime() {
        return time;
    }

    @Override
    public int getProductCompoundFrequency() {
        return compoundFrequency;
    }

    @Override
    public void changeInterestRate(double rate) {
    interestRate=rate;
    }

    @Override
    public boolean isDeposit() {
        return true;

    }

    @Override
    public boolean isLoan() {
        return false;
    }
    public void finishDepositEarly(Account accountToGiveMoneyTo){//usun obiekt

    }

    int getTimeLeft(){
        return this.timeLeft;
    }
    void setTimeLeft(int timeleft){
        this.timeLeft=timeleft;
    }
}
