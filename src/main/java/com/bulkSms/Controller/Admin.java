package com.bulkSms.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class Admin {

    @PostMapping("/message")
    public ResponseEntity<String> postMessage() {
        return ResponseEntity.ok("Message received by admin successfully!");

    }
}

