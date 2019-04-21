package software.jevera.domain.dto;

import lombok.Data;

@Data
public class TopUpDto {

    private String cardNumber;

    private String cvv;

    private Integer amount;

}
