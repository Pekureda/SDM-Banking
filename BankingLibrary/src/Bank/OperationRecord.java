package Bank;

import javax.swing.plaf.MenuBarUI;
import java.time.LocalDate;

public class OperationRecord{

    public enum Status{
        SUCCESS,
        FAIL
    }
    private Status status;
    private LocalDate execution;
    private String description;
    private String operationCode;

    OperationRecord(Status status, String description, Operation op)
    {
        this.status = status;
        execution = LocalDate.now();
        this.description = description;
        this.operationCode = op.getOperationCode();
    }

    public LocalDate getExecution() {
        return execution;
    }

    public String getDescription() {
        return description;
    }

    public String getOperationCode() {
        return operationCode;
    }
}