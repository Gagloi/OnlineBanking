package software.jevera.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;

@Data
@EqualsAndHashCode(of = "cardNumber")
public class CardDto {

    @Length(max = 16, min = 16)
    private String cardNumber;

    @Length(max = 3, min = 3)
    private String cvv;

    private Instant endDate;

}
