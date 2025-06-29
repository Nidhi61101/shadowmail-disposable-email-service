package com.shadowmail.disposable.controller;

import com.shadowmail.disposable.repository.DisposableEmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/shadowmail/email/")
public class ReceiveEmailController {

    private static final Logger log = LoggerFactory.getLogger(ReceiveEmailController.class);

    @Autowired
    private DisposableEmailRepository disposableEmailRepository;
//    @Autowired
//    private EmailReceiverService emailReceiverService;
//
//    @PostMapping("receive")
//    private ResponseEntity<String> receiveEmail(@RequestBody EmailContent emailContent) {
//        try {
//            log.info("Received email from: {}, to: {}, subject: {}",
//                     emailContent.getFromUser(),
//                     emailContent.getToUser(),
//                     emailContent.getSubject());
//            emailReceiverService.receiveEmail(emailContent);
//            return ResponseEntity.ok("Email received and moved to disposable inbox");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error processing email: " + e.getMessage());
//        }
//    }

    @GetMapping("getExpiry")
    public ResponseEntity<Map<String, String>> getExpiry(@RequestParam String emailAddress){
        log.info("Received request to get expiry for email address: {}", emailAddress);

        try{
            String expiry = disposableEmailRepository.findByEmailAddress(emailAddress)
                    .map(email -> email.isActive() ? email.getExpiresAt().toString() : "Expired")
                    .orElseThrow(() -> new RuntimeException("Email address not found"));
            log.info("Expiry time for email address {}: {}", emailAddress, expiry);
            Map<String, String> response = new HashMap<>();
            response.put("ExpiresAt", expiry);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            log.error("Error fetching expiry for email address {} due to {}", emailAddress, e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
