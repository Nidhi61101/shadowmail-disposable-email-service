package com.shadowmail.disposable.controller;


import com.shadowmail.disposable.model.DisposableEmail;
import com.shadowmail.disposable.model.EmailDTO;
import com.shadowmail.disposable.service.DisposableEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shadowmail/email/")
public class DisposableEmailController {

    private static final Logger log = LoggerFactory.getLogger(DisposableEmailController.class);
    @Autowired
    private final DisposableEmailService disposableEmailService;

    public DisposableEmailController(DisposableEmailService disposableEmailService) {
        this.disposableEmailService = disposableEmailService;
    }

    @PostMapping("create")
    public ResponseEntity<Map<String, String>>  createEmailAddress() {
        log.info("Creating new disposable email address");
        DisposableEmail email = disposableEmailService.createEmailAddress();
        Map<String, String> response = new HashMap<>();
        response.put("EmailAddress", email.getEmailAddress());
        return ResponseEntity.ok(response);
    }

    @GetMapping("inbox")
    public ResponseEntity<?> getInbox(@RequestParam String emailAddress){
        try {
            List<EmailDTO> inbox = disposableEmailService.getInbox(emailAddress);
            return ResponseEntity.ok(inbox);
        }catch (Exception e){
            log.error("Error fetching inbox for {} : {}",emailAddress,e.getMessage());
            return ResponseEntity.badRequest().body("Error fetching inbox:" + e.getMessage());
        }
    }
}
