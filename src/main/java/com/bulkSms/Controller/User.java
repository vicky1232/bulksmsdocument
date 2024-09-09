package com.bulkSms.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class User {

    @PostMapping("/message")
    public ResponseEntity<String> postMessage() {
        return ResponseEntity.ok("Message received by user successfully!");
    }

}