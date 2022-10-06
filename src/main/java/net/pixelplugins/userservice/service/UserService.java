package net.pixelplugins.userservice.service;

import lombok.AllArgsConstructor;
import net.pixelplugins.userservice.dto.authentication.request.CreateUserRequest;
import net.pixelplugins.userservice.dto.profile.response.UserDetailsResponse;
import net.pixelplugins.userservice.entity.User;
import net.pixelplugins.userservice.exception.EmailAlreadyRegisteredException;
import net.pixelplugins.userservice.exception.UserNotFoundException;
import net.pixelplugins.userservice.exception.UsernameAlreadyRegisteredException;
import net.pixelplugins.userservice.model.UserModel;
import net.pixelplugins.userservice.model.type.Role;
import net.pixelplugins.userservice.repository.UserRepository;

import net.pixelplugins.userservice.util.JWTUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final JWTUtil jwt;

    public UserDetailsResponse findByUsername(String username) throws UserNotFoundException {
        return repository.findByUsername(username)
                .map(user -> new UserDetailsResponse(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getPassword()))
                .orElseThrow(UserNotFoundException::new);
    }

    public UserDetailsResponse findByToken(String token) throws UserNotFoundException {
        return findByUsername(jwt.findUsernameByToken(token));
    }

    public UserDetailsResponse findByEmail(String email) throws UserNotFoundException {
        return repository.findByEmail(email)
                .map(user -> new UserDetailsResponse(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getPassword()))
                .orElseThrow(UserNotFoundException::new);
    }

    public UserDetailsResponse findById(long id) throws UserNotFoundException {
        return repository.findById(id)
                .map(user -> new UserDetailsResponse(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getPassword()))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(UserModel::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
