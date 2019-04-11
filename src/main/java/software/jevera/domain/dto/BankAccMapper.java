package software.jevera.domain.dto;

import org.mapstruct.Mapper;
import software.jevera.domain.BankAccount;

@Mapper(componentModel = "spring")
public interface BankAccMapper {

    BankAccount toBankaccount(BankAccountDto bankAccountDto);

}
