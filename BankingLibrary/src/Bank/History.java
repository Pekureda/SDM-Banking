package Bank;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<OperationRecord> operations;

    public History(){
        operations = new ArrayList<OperationRecord>();
    }
    public List<OperationRecord> getOperations() {
        return operations;
    }
}
