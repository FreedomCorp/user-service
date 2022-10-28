package net.pixelplugins.userservice.service;

import lombok.AllArgsConstructor;

import net.pixelplugins.userservice.dto.request.CreateUserRequest;
import net.pixelplugins.userservice.dto.request.MatchUserPasswordRequest;
import net.pixelplugins.userservice.dto.request.UpdateUserRequest;
import net.pixelplugins.userservice.dto.response.ReadUserResponse;
import net.pixelplugins.userservice.dto.response.ValueCheckResponse;
import net.pixelplugins.userservice.entity.User;
import net.pixelplugins.userservice.exception.UserNotFoundException;
import net.pixelplugins.userservice.exception.UsernameAlreadyRegisteredException;
import net.pixelplugins.userservice.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public ReadUserResponse create(CreateUserRequest request) throws UsernameAlreadyRegisteredException {
        if (repository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyRegisteredException();
        }

        var user = repository.save(User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_USER").build());

        return new ReadUserResponse(user);
    }

    public ValueCheckResponse existsByUsername(String username) {
        return new ValueCheckResponse(repository.existsByUsername(username));
    }

    public ReadUserResponse findById(long id) throws UserNotFoundException {
        return new ReadUserResponse(repository.findById(id)
                .orElseThrow(UserNotFoundException::new));
    }

    public ReadUserResponse findByUsername(String username) throws UserNotFoundException {
        return new ReadUserResponse(repository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new));
    }

    public ReadUserResponse update(UpdateUserRequest request) throws UserNotFoundException {
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return new ReadUserResponse(repository.save(user));
    }

    public ValueCheckResponse match(String username, String password) throws UserNotFoundException {
        var user = repository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        return new ValueCheckResponse((passwordEncoder.matches(password, user.getPassword())));
    }

    public ValueCheckResponse match(MatchUserPasswordRequest request) throws UserNotFoundException {
        return match(request.getUsername(), request.getPassword());
    }

    public String findPasswordByUsername(String username) throws UserNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new)
                .getPassword();
    }
}
