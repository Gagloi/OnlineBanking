package software.jevera.domain.dto;

import org.mapstruct.Mapper;
import software.jevera.domain.Card;

@Mapper(componentModel = "spring")
public interface CardMapper {

    Card toCard(CardDto cardDto);

}
