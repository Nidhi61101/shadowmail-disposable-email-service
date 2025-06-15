package com.shadowmail.disposable.service;

import com.shadowmail.disposable.model.DisposableEmail;
import com.shadowmail.disposable.model.Email;
import com.shadowmail.disposable.model.EmailContent;
import com.shadowmail.disposable.repository.DisposableEmailRepository;
import com.shadowmail.disposable.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailReceiverService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private DisposableEmailRepository disposableEmailRepository;

    public void receiveEmail(EmailContent emailContent) {
        String recipientEmail = emailContent.getToUser();
        if (disposableEmailRepository.existsByEmailAddress(recipientEmail)) {
            DisposableEmail disposableEmail = disposableEmailRepository.findByEmailAddress(recipientEmail)
                    .orElseThrow(() -> new RuntimeException("Recipient email not found"));
            LocalDateTime expiresAt = disposableEmail.getExpiresAt();
            if (expiresAt == null) {
                throw new RuntimeException("Email address has no expiration time");
            }
            if (expiresAt.isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Email address has expired");
            }
            if (expiresAt.isAfter(LocalDateTime.now())) {
                Email email = new Email();
                email.setDisposableEmail(disposableEmail);
                email.setReceivedAt(LocalDateTime.now());
                email.setContent(emailContent.getBody());
                email.setSubject(emailContent.getSubject());
                email.setFromAddress(email.getFromAddress());
                emailRepository.save(email);
            }
        }
    }
}
