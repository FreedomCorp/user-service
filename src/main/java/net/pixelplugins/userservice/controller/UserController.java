package net.pixelplugins.userservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.pixelplugins.userservice.dto.request.CreateUserRequest;
import net.pixelplugins.userservice.dto.request.MatchUserPasswordRequest;
import net.pixelplugins.userservice.dto.request.UpdateUserRequest;
import net.pixelplugins.userservice.dto.response.ReadUserResponse;
import net.pixelplugins.userservice.dto.response.ValueCheckResponse;
import net.pixelplugins.userservice.exception.UserNotFoundException;
import net.pixelplugins.userservice.exception.UsernameAlreadyRegisteredException;
import net.pixelplugins.userservice.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ReadUserResponse create(@RequestBody CreateUserRequest request) throws UsernameAlreadyRegisteredException {
        return service.create(request);
    }

    @GetMapping("/exists")
    @ResponseStatus(HttpStatus.OK)
    public ValueCheckResponse exists(@RequestParam String username) {
        return service.existsByUsername(username);
    }

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public ReadUserResponse findByUsername(@RequestParam String username) throws UserNotFoundException {
        return service.findByUsername(username);
    }

    @GetMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public String findPasswordByUsername(@RequestParam String username) throws UserNotFoundException {
        return service.findPasswordByUsername(username);
    }

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReadUserResponse findById(@PathVariable long id) throws UserNotFoundException {
        return service.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ReadUserResponse update(@RequestBody UpdateUserRequest request) throws UserNotFoundException {
        return service.update(request);
    }

    @PostMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public ValueCheckResponse matchPassword(@RequestBody MatchUserPasswordRequest request) throws UserNotFoundException {
        return service.match(request);
    }

}
