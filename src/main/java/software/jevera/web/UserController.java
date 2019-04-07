package software.jevera.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.jevera.domain.User;
import software.jevera.domain.UserDto;
import software.jevera.service.UserService;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final HttpSession httpSession;

    @PostMapping("/register")
    public User register(UserDto userDto){

        return userService.registerUser(userDto);

    }

    @PostMapping("/login")
    public User login(UserDto userDto){
        User user = userService.login(userDto);
        httpSession.setAttribute("user", user);
        return userService.login(userDto);

    }


}
