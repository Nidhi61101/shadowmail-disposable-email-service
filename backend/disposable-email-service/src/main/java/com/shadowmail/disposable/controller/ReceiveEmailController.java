package com.shadowmail.disposable.controller;

import com.shadowmail.disposable.model.EmailContent;
import com.shadowmail.disposable.service.EmailReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shadowmail/email/")
public class ReceiveEmailController {

    @Autowired
    private EmailReceiverService emailReceiverService;

    @PostMapping("receive")
    private ResponseEntity<String> receiveEmail(@RequestBody EmailContent emailContent){
        var result = emailReceiverService.receiveEmail(emailContent);
        return ResponseEntity.ok("Email received and moved to disposable inbox");
    }
}
