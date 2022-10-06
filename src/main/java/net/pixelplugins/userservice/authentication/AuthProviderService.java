package net.pixelplugins.userservice.authentication;

import lombok.AllArgsConstructor;
import net.pixelplugins.userservice.exception.UserNotFoundException;
import net.pixelplugins.userservice.model.UserModel;
import net.pixelplugins.userservice.repository.UserRepository;
import net.pixelplugins.userservice.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@AllArgsConstructor
public class AuthProviderService implements AuthenticationProvider {

    private UserRepository repository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var username = authentication.getName();
        var password = authentication.getCredentials().toString();

        var optUser = repository.findByUsername(username);

        if (optUser.isPresent()) {
            var user = optUser.get();
            var model = new UserModel(user);

            return new UsernamePasswordAuthenticationToken(username, password, model.getAuthorities());
        }

        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
