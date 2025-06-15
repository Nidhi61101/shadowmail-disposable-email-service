package com.shadowmail.disposable.controller;

import com.shadowmail.disposable.model.EmailContent;
import com.shadowmail.disposable.service.EmailReceiverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shadowmail/email/")
public class ReceiveEmailController {

    private static final Logger log = LoggerFactory.getLogger(ReceiveEmailController.class);
    @Autowired
    private EmailReceiverService emailReceiverService;

    @PostMapping("receive")
    private ResponseEntity<String> receiveEmail(@RequestBody EmailContent emailContent) {
        try {
            log.info("Received email from: {}, to: {}, subject: {}",
                     emailContent.getFromUser(),
                     emailContent.getToUser(),
                     emailContent.getSubject());
            emailReceiverService.receiveEmail(emailContent);
            return ResponseEntity.ok("Email received and moved to disposable inbox");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing email: " + e.getMessage());
        }
    }
}
