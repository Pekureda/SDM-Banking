public interface ReportableVisitable {

    String accept(Reporter_visitor visitor);//aby klasa byla mozliwa do wizytowalna musi implementowac metodę accept visitor
}
