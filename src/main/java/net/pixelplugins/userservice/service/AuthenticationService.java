package net.pixelplugins.userservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.pixelplugins.userservice.dto.authentication.request.CreateUserRequest;
import net.pixelplugins.userservice.dto.authentication.request.LoginUserRequest;
import net.pixelplugins.userservice.dto.authentication.response.AuthenticateUserResponse;
import net.pixelplugins.userservice.dto.profile.response.UserDetailsResponse;
import net.pixelplugins.userservice.entity.User;
import net.pixelplugins.userservice.exception.EmailAlreadyRegisteredException;
import net.pixelplugins.userservice.exception.UsernameAlreadyRegisteredException;
import net.pixelplugins.userservice.model.UserModel;
import net.pixelplugins.userservice.model.type.Role;
import net.pixelplugins.userservice.repository.UserRepository;
import net.pixelplugins.userservice.util.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class AuthenticationService implements UserDetailsService {

    private final UserRepository repository;

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwt;
    private final PasswordEncoder encoder;

    public AuthenticateUserResponse authenticate(LoginUserRequest request) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        var token = jwt.generate(authentication);
        var user = (UserModel) authentication.getPrincipal();

        return new AuthenticateUserResponse(token, user.getId(), user.getUsername(), user.getEmail());
    }

    public AuthenticateUserResponse register(CreateUserRequest request) throws UsernameAlreadyRegisteredException, EmailAlreadyRegisteredException {
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyRegisteredException();
        }

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyRegisteredException();
        }

        repository.save(User.builder()
                .role(Role.ROLE_USER)
                .email(request.getEmail())
                .name(request.getName())
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .build());

        return authenticate(new LoginUserRequest(request.getUsername(), request.getPassword()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(UserModel::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

}
