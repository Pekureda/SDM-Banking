package Bank;

public class ReporterStringVisitor implements ReporterVisitor {


    @Override
    public String reportVisitLoan(Loan loan) {

        StringBuilder sb = new StringBuilder();
        sb.append(loan.internalStateToReport);
        return sb.toString();
    }
}
