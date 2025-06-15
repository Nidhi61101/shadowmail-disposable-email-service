package com.shadowmail.disposable.controller;


import com.shadowmail.disposable.model.DisposableEmail;
import com.shadowmail.disposable.repository.DisposableEmailRepository;
import com.shadowmail.disposable.service.DisposableEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
        response.put("ExpiresAt", email.getExpiresAt().toString());
        return ResponseEntity.ok(response);
    }
}
