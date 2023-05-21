package Bank;

import java.time.LocalDate;

public class Obsolete_OperationRecord {

    public enum Status{
        SUCCESS,
        FAIL
    }
    private Status status;
    private LocalDate execution;
    private String description;
    private String operationCode;

    Obsolete_OperationRecord(Status status, String description, Obsolete_Operation op)
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