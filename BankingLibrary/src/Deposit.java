public class Deposit implements InterestApplicableProduct{
    public float current_deposit=0;
    public float deposit_total_money_time;
    public double principal;
    public double rate;
    public int time;
    public int compoundFrequency;
    public boolean isDeposit=true;

    Deposit(float money){
        current_deposit=money;
        deposit_total_money_time=10;//months

    }

    @Override
    public double getProductPrincipal() {
        return 0;
    }

    @Override
    public double getProductRate() {
        return 0;
    }

    @Override
    public int getProductTime() {
        return 0;
    }

    @Override
    public int getProductCompoundFrequency() {
        return 0;
    }

    @Override
    public double getCurrent_deposit() {
        return 0;
    }

    @Override
    public int getDeposit_total_money_time() {
        return 0;
    }

    @Override
    public boolean isDeposit() {
        return false;
    }
}
