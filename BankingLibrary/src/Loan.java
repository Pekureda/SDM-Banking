public class Loan implements ReportableVisitable , InterestApplicableProduct{

    String internalStateToReport="udalo sie zrobic raport, tutaj jest jedna wartosc ktora wrzucilem do raportu"+
            "ale mam dostep do tej klasy, takze moge wrzucic cokolwiek do raportu, implementacja raportu jest w klasie reportString_visitor";

    @Override
    public String accept(Reporter_visitor visitor) {
        return visitor.reportVisitLoan(this);
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
