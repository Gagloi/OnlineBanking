package software.jevera.service.bankaccount;

import lombok.ToString;

@ToString
public enum BankAccountStateEnum {
    ACTIVE, BLOCKED_BY_BANK, BLOCKED_BY_USER, RESTORED
    //CREATED(созданный), CONFIRMED(подтвержденный), REJECTED(не подтвержденный), BLOCKED_BY_BANK(заблокированный банком)
    // BLOCKED_BY_USER(заблокированный владельцем), RESTORED(восстановленный)
}
