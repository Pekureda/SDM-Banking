public class Loan implements ReportableVisitable{

    String internalStateToReport="udalo sie zrobic raport, tutaj jest jedna wartosc ktora wrzucilem do raportu"+
            "ale mam dostep do tej klasy, takze moge wrzucic cokolwiek do raportu, implementacja raportu jest w klasie reportString_visitor";

    @Override
    public String accept(Reporter_visitor visitor) {
        return visitor.reportVisitLoan(this);
    }
}
