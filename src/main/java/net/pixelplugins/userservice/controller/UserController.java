package net.pixelplugins.userservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.pixelplugins.userservice.dto.profile.response.UserDetailsResponse;
import net.pixelplugins.userservice.exception.UserNotFoundException;
import net.pixelplugins.userservice.model.UserModel;
import net.pixelplugins.userservice.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService service;

    @GetMapping("/me")
    public UserDetailsResponse find(@AuthenticationPrincipal UserModel user) throws UserNotFoundException {
        return service.findByUsername(user.getUsername());
    }

}
