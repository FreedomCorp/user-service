package net.pixelplugins.userservice.dto.profile.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class UserDetailsResponse {

    private long id;

    private String name;
    private String username;

    private String email;

    private String passwordHash;
}
