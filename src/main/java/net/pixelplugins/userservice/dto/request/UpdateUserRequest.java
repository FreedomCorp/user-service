package net.pixelplugins.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {

    private String username;

    private String name;
    private String password;

}
