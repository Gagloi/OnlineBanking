package software.jevera.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;

@Data
public class BankAccountDto {
    @Length(max = 40, min = 5)
    private String name;
    private Instant creationDate;
}
