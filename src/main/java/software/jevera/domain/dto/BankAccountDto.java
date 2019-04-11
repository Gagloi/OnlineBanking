package software.jevera.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;

@Data
public class BankAccountDto {
    private Instant creationDate;
}
