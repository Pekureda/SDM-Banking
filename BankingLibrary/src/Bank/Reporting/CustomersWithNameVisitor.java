package Bank.Reporting;

import Bank.Customer;

import java.util.Objects;

public class CustomersWithNameVisitor implements CustomerVisitor {
    public final String name;
    public CustomersWithNameVisitor(String name) {
        this.name = name;
    }
    @Override
    public Customer visit(Customer customer) {
        return Objects.equals(customer.getName(), name) ? customer : null;
    }
}
