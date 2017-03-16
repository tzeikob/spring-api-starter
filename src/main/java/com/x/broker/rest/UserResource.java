package com.x.broker.rest;

import com.x.broker.data.UserRepository;
import com.x.broker.domain.User;
import com.x.broker.exc.UserNotFoundException;
import com.x.broker.exc.DuplicateUsernameException;
import com.x.broker.exc.InvalidUserFormException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> get() {
        List<User> users = userRepository.select();

        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> findUserByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException(username);
        }

        return new ResponseEntity<User>(user, HttpStatus.FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> create(@RequestBody @Valid User user) {
        String username = user.getUsername();

        if (userRepository.isUsernameTaken(username)) {
            throw new DuplicateUsernameException(user.getUsername());
        }

        userRepository.save(user);

        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<User> update(@RequestBody @Valid User data) {
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

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> delete(@PathVariable Long id) {
        User user = userRepository.find(id);

        if (user == null) {
            throw new UserNotFoundException(id);
        }

        userRepository.delete(user);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
