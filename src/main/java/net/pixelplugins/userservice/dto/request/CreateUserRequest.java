package net.pixelplugins.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateUserRequest {

    private String name;
    private String username;
    private String email;
    private String password;

}
