package main.java.software.jevera.service.bankaccount;

public enum BankAccountStateEnum {
    CREATED, CONFIRMED, REJECTED, BLOCKED_BY_BANK, BLOCKED_BY_USER, RESTORED
    //CREATED(созданный), CONFIRMED(подтвержденный), REJECTED(не подтвержденный), BLOCKED_BY_BANK(заблокированный банком)
    // BLOCKED_BY_USER(заблокированный владельцем), RESTORED(восстановленный)
}
