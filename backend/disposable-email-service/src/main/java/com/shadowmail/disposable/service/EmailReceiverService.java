package com.shadowmail.disposable.service;

import com.shadowmail.disposable.controller.ReceiveEmailController;
import com.shadowmail.disposable.model.DisposableEmail;
import com.shadowmail.disposable.model.Email;
import com.shadowmail.disposable.model.EmailContent;
import com.shadowmail.disposable.repository.DisposableEmailRepository;
import com.shadowmail.disposable.repository.EmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailReceiverService {
    private static final Logger log = LoggerFactory.getLogger(EmailReceiverService.class);
    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private DisposableEmailRepository disposableEmailRepository;

    public void receiveEmail(EmailContent emailContent) {
        String recipientEmail = emailContent.getToUser();
        if (disposableEmailRepository.existsByEmailAddress(recipientEmail)) {
            log.info("Received email for disposable address : {}", recipientEmail);
            DisposableEmail disposableEmail = disposableEmailRepository.findByEmailAddress(recipientEmail)
                    .orElseThrow(() -> new RuntimeException("Recipient email not found"));
            LocalDateTime expiresAt = disposableEmail.getExpiresAt();
            if (expiresAt == null) {
                log.error("Email address has no expiration time");
                throw new RuntimeException("Email address has no expiration time");
            }
            if (expiresAt.isBefore(LocalDateTime.now())) {
                log.error("Email address has expired , please recreate");
                throw new RuntimeException("Email address has expired");
            }
            if (expiresAt.isAfter(LocalDateTime.now())) {
                log.info("Storing email for disposable address: {}", recipientEmail);
                Email email = new Email();
                email.setDisposableEmail(disposableEmail);
                email.setReceivedAt(LocalDateTime.now());
                email.setContent(emailContent.getBody());
                email.setSubject(emailContent.getSubject());
                email.setFromAddress(emailContent.getFromUser());
                emailRepository.save(email);
                log.info("Email stored successfully");
            }
        } else {
            log.info("Received email for non-disposable address: {}",recipientEmail);
            throw new RuntimeException("Recipient email is not a disposable email address");
        }
    }
}
