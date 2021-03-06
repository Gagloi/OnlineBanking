package software.jevera.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "login")
public class User {
    @JsonIgnore
    private String passwordHash;
    private String login;

//    public User(String passwordHash, String login) {
//        this.passwordHash = passwordHash;
//        this.login = login;
//    }
//
//    public String getPasswordHash() {
//        return passwordHash;
//    }
//
//    public void setPasswordHash(String passwordHash) {
//        this.passwordHash = passwordHash;
//    }
//
//    public String getLogin() {
//        return login;
//    }
//
//    public void setLogin(String login) {
//        this.login = login;
//    }
//
    public User(String login) {
        this.login = login;
    }
}
