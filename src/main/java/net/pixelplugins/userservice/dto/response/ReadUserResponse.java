package net.pixelplugins.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.pixelplugins.userservice.entity.User;

@AllArgsConstructor
@Getter
@Setter
public class ReadUserResponse {

    private long id;

    private String name;
    private String username;
    private String email;

    public ReadUserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

}
