public class ReporterString_visitor implements Reporter_visitor{


    @Override
    public String reportVisitLoan(Loan loan) {

        StringBuilder sb = new StringBuilder();
        sb.append(loan.internalStateToReport);
        return sb.toString();
    }
}
