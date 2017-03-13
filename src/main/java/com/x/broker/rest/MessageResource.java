package com.x.broker.rest;

import com.x.broker.domain.BasicMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageResource {

    @RequestMapping(value = "/echo/{text}", method = RequestMethod.GET)
    public ResponseEntity<BasicMessage> echo(@PathVariable String text) {
        BasicMessage message = new BasicMessage(1L, text);

        return new ResponseEntity<BasicMessage>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/echo", method = RequestMethod.POST)
    public ResponseEntity<BasicMessage> echo(@RequestBody BasicMessage message) {
        return new ResponseEntity<BasicMessage>(message, HttpStatus.OK);
    }
}
