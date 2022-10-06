package net.pixelplugins.userservice.dto.authentication.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthenticateUserResponse {

    private String token;
    private long id;

    private String username;
    private String email;

}
