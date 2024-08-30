package dev.thomasrba.um.service;

import dev.thomasrba.um.entity.newPasswordRequest;
import dev.thomasrba.um.entity.user.Role;
import dev.thomasrba.um.entity.user.User;
import dev.thomasrba.um.exception.BadPermissionException;
import dev.thomasrba.um.exception.PasswordException;
import dev.thomasrba.um.exception.UserNotFoundException;
import dev.thomasrba.um.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public void deleteUser(User user, long id) {
        if (user.getRole() != Role.ADMIN)
            throw new BadPermissionException("You do not have permission to delete this User !");
        User userToDelete = userRepository.findById(id).orElse(null);
        if (userToDelete == null)
            throw new UserNotFoundException("User with id " + id + " not found for deletion !");
        userRepository.delete(userToDelete);
    }

    public void updateUser(User userLogged, User user, long id) {
        Optional<User> userToUpdate = userRepository.findById(id);
        if (userToUpdate.isPresent()) {
            User existingUser = userToUpdate.get();
            if (Objects.equals(userLogged.getId(), existingUser.getId()) || userLogged.getRole() == Role.ADMIN) {
                existingUser.setFirstname(user.getFirstname());
                existingUser.setLastname(user.getLastname());
                existingUser.setEmail(user.getEmail());
                existingUser.setRole(user.getRole());
                userRepository.save(existingUser);
            }else
                throw new BadPermissionException("Permission denied to update this user !");
        } else {
            throw new UserNotFoundException("User with id " + id + " not found for update !");
        }
    }

    public void changePassword(newPasswordRequest newPasswordRequest, Principal userConnected) {
        var user = (User) ((UsernamePasswordAuthenticationToken)userConnected).getPrincipal();

        if (!passwordEncoder.matches(user.getPassword(), newPasswordRequest.getPassword())) {
            throw new PasswordException("Wrong current password !");
        }
        if (!newPasswordRequest.getNewPassword().equals(newPasswordRequest.getConfirmNewPassword())) {
            throw new PasswordException("New passwords do not match !");
        }

        user.setPassword(passwordEncoder.encode(newPasswordRequest.getNewPassword()));
        userRepository.save(user);
    }
}
