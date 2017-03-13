package com.x.broker.rest;

import com.x.broker.data.UserRepository;
import com.x.broker.domain.User;
import javax.ws.rs.core.MediaType;
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
@RequestMapping(value = "/user",
        produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML},
        consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UserResource {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<User> getByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            return new ResponseEntity<User>(user, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<User> create(@RequestBody User user) {
        try {
            userRepository.save(user);
        } catch (Exception exc) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }
}
