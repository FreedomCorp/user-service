package net.pixelplugins.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MatchUserPasswordRequest {

    private String username;
    private String password;

}
