package dev.thomasrba.um.controller;

import dev.thomasrba.um.entity.newPasswordRequest;
import dev.thomasrba.um.entity.user.User;
import dev.thomasrba.um.service.AuthenticationService;
import dev.thomasrba.um.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<User> getUser() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @GetMapping(path = "profile")
    public User getUserProfile() {
        return authenticationService.getLogged();
    }

    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable long id) {
        User user = authenticationService.getLogged();
        userService.deleteUser(user, id);
    }

    @PutMapping(path = "{id}")
    public void updateUser(@PathVariable long id, @RequestBody User user)
    {
        User userLogged = authenticationService.getLogged();
        userService.updateUser(userLogged, user, id);
    }

    @PatchMapping
    public ResponseEntity<?> updatePasswordUser(@RequestBody newPasswordRequest newPasswordRequest, Principal userConnected) {
        userService.changePassword(newPasswordRequest, userConnected);
        return ResponseEntity.ok().build();
    }
}
