package Bank;

import java.util.Currency;
import java.lang.Math;

public class Loan extends Account implements ReportableVisitable , InterestApplicableProduct{

    String internalStateToReport="udalo sie zrobic raport, tutaj jest jedna wartosc ktora wrzucilem do raportu"+
            "ale mam dostep do tej klasy, takze moge wrzucic cokolwiek do raportu, implementacja raportu jest w klasie reportString_visitor";

    //Customer ownerOfLoan;
    //owner

    double amountToRepay;
    double principalAmount;
    double interestRate;
    int time;//na ile miesiecy wzieta pozyczka
    int timeLeft;//ile miesiecy zostalo trzeba to jakos aktualizowac
    int compoundFrequency; //co ile miesiecy dodaja sie odsetki
    boolean runningBelowInCredits;



    @Override
    public String accept(ReporterVisitor visitor) {
        return visitor.reportVisitLoan(this);
    }

    @Override
    public double getProductPrincipalAmount() {
        return principalAmount;
    }

    @Override
    public double getProductRate() {
        return interestRate;
    }

    @Override
    public int getProductTime() {
        return this.time;
    }

    @Override
    public void setProductTime(int time) {
        this.time=time;
    }

    @Override
    public int getProductCompoundFrequency() {
        return this.compoundFrequency;
    }

    @Override
    public void setProductCompoundFrequency(int compoundFrequency) {
         this.compoundFrequency=compoundFrequency;
    }

    @Override
    public void setProductPrincipalAmount(double newPrincipalAmount) {
        this.principalAmount=newPrincipalAmount;
    }

    @Override
    public void setInterestRate(double rate) {
        interestRate=rate;

    }

    @Override
    public Double calculateInterest() {
        return  myInterestRateStrategy.calculateInterest(this);
    }

    @Override
    public boolean isDeposit() {
        return false;
    }

    @Override
    public void setInterestRateStrategy(InterestRateStrategy concreteStrategy) {
        this.myInterestRateStrategy=concreteStrategy;
    }

    @Override
    public boolean isLoan() {
        return true;
    }

    Loan(Customer owner, Currency currency, double principalAmount, double rate, int time,int compoundFrequency,
         InterestRateStrategy concreteInterestRateStrategyStrategy){
        super( owner,  principalAmount,  currency);
        this.principalAmount=principalAmount;
        this.interestRate=rate;
        this.time=time;
        this.amountToRepay=principalAmount;
        this.compoundFrequency=compoundFrequency;
        super.myInterestRateStrategy=concreteInterestRateStrategyStrategy;

    }

    public void addToLoan(double amount){//przydatne w przypadku odsetek
        this.amountToRepay+=amount;
    }

    public void repayLoan(double amount){
        this.amountToRepay-=amount;
        super.balance-=amount;
        //super.balance-=amount; //trzeba by sobie odjac kase jak sie to wywoluje
        if(super.balance<0){
            runningBelowInCredits=true;
        }
        if(this.amountToRepay<0.01){
            this.amountToRepay=0;
        }
        if(this.amountToRepay<0){
            this.amountToRepay=0;
            super.balance=super.balance+ Math.abs( this.amountToRepay);
        }

    }

    int getTimeLeft(){
        return this.timeLeft;
    }
    void setTimeLeft(int timeleft){
        this.timeLeft=timeleft;
    }

    public void setAmountToRepay(double amountToRepay) {
        this.amountToRepay = amountToRepay;
    }
    public double getAmountToRepay() {
        return amountToRepay;
    }
}
