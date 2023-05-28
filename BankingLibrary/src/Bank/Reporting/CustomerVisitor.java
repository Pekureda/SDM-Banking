package Bank.Reporting;

import Bank.Customer;

public interface CustomerVisitor {
    Customer visit(Customer customer);
}
