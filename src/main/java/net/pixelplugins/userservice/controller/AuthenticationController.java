package net.pixelplugins.userservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.pixelplugins.userservice.dto.authentication.request.CreateUserRequest;
import net.pixelplugins.userservice.dto.authentication.request.LoginUserRequest;
import net.pixelplugins.userservice.dto.authentication.response.AuthenticateUserResponse;
import net.pixelplugins.userservice.exception.EmailAlreadyRegisteredException;
import net.pixelplugins.userservice.exception.UsernameAlreadyRegisteredException;
import net.pixelplugins.userservice.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticateUserResponse authenticate(@RequestBody LoginUserRequest request) {
        return service.authenticate(request);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticateUserResponse register(@RequestBody CreateUserRequest request) throws UsernameAlreadyRegisteredException, EmailAlreadyRegisteredException {
        return service.register(request);
    }

}
