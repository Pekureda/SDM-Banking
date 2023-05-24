package Bank;

import java.util.Currency;

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
        return 0;
    }

    @Override
    public int getProductCompoundFrequency() {
        return this.compoundFrequency;
    }

    @Override
    public void changeInterestRate(double rate) {
        interestRate=rate;

    }

    @Override
    public boolean isDeposit() {
        return false;
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

    }

    void addToLoan(double amount){//przydatne w przypadku odsetek
        this.amountToRepay+=amount;
    }
    void repayLoan(double amount){
        this.amountToRepay-=amount;
        super.balance-=amount; //trzeba by sobie odjac kase jak sie to wywoluje
        if(super.balance<0){
            runningBelowInCredits=true;
        }

    }

    int getTimeLeft(){
        return this.timeLeft;
    }
    void setTimeLeft(int timeleft){
        this.timeLeft=timeleft;
    }

}
