package software.jevera.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "cardNumber")
public class CardDto {

    private String cardNumber;

    private String cvv;

    private String endDate;

}
