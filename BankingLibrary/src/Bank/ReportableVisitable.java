package Bank;

public interface ReportableVisitable {

    String accept(ReporterVisitor visitor);//aby klasa byla mozliwa do wizytowalna musi implementowac metodę accept visitor
}
