package com.x.broker.rest;

import com.x.broker.data.UserRepository;
import com.x.broker.domain.User;
import com.x.broker.exc.UserNotFoundException;
import com.x.broker.exc.DuplicateUsernameException;
import com.x.broker.exc.InvalidUserFormException;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * A RESTful resource controller for user entities.
 *
 * @author Akis Papadopoulos
 */
@RestController
@RequestMapping("/user")
public class UserResource {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> get() {
        List<User> users = userRepository.select();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> findUserByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException(username);
        }

        return ResponseEntity.status(HttpStatus.FOUND).body(user);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid User user, UriComponentsBuilder uriBuilder) {
        String username = user.getUsername();

        if (userRepository.isUsernameTaken(username)) {
            throw new DuplicateUsernameException(user.getUsername());
        }

        userRepository.save(user);

        URI uri = uriBuilder.path("/user/{username}")
                .buildAndExpand(username)
                .toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid User data) {
        Long id = data.getId();

        if (data.getId() == null) {
            throw new InvalidUserFormException("Invalid user form, not valid id value");
        }

        User user = userRepository.find(id);

        String username = data.getUsername();

        if (!username.equals(user.getUsername())
                && userRepository.isUsernameTaken(username)) {
            throw new DuplicateUsernameException(username);
        }

        user.setUsername(username);
        user.setEnabled(data.isEnabled());
        user.setCreatedDate(data.getCreatedDate());

        userRepository.update(user);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        User user = userRepository.find(id);

        if (user == null) {
            throw new UserNotFoundException(id);
        }

        userRepository.delete(user);

        return ResponseEntity.ok(user);
    }
}
