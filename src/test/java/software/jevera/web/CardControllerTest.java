package software.jevera.web;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import software.jevera.domain.User;
import software.jevera.service.CardService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BankAccountController.class)
@ContextConfiguration(classes = {CardController.class})
public class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @Autowired
    private CardController cardController;

    @Test
    @SneakyThrows
    public void getCards() {

        Map<String, Object> sessionattr = new HashMap<>();
        sessionattr.put("user", new User());

        mockMvc.perform(get("/api/cards").sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk());

    }
}