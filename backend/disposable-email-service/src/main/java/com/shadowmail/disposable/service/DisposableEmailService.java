package com.shadowmail.disposable.service;

import com.shadowmail.disposable.model.DisposableEmail;
import com.shadowmail.disposable.model.EmailDTO;
import com.shadowmail.disposable.repository.DisposableEmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DisposableEmailService {

    private static final Logger log = LoggerFactory.getLogger(DisposableEmailService.class);
    @Autowired
    private final DisposableEmailRepository disposableEmailRepository;

    public DisposableEmailService(DisposableEmailRepository disposableEmailRepository) {
        this.disposableEmailRepository = disposableEmailRepository;
    }

    public DisposableEmail createEmailAddress() {
        DisposableEmail email = new DisposableEmail();
        String emailAddress = generateEmailAddress();
        email.setCreatedAt(LocalDateTime.now());
        email.setExpiresAt(LocalDateTime.now().plusMinutes(60));
        email.setEmailAddress(emailAddress);
        disposableEmailRepository.save(email);
        log.info("Created new disposable email address: {}", emailAddress);
        return email;
    }

    private String generateEmailAddress() {
        String domain = "@shadowmail.com";
        String emailAddress;
        do {
            emailAddress = UUID.randomUUID().toString().substring(0, 8) + domain;
        } while (disposableEmailRepository.existsByEmailAddress(emailAddress));
        log.info("Generated new disposable email address: {}", emailAddress);
        return emailAddress;
    }

    public List<EmailDTO> getInbox(String emailAddress) {
        log.info("Fetching inbox for disposable email address: {}", emailAddress);
        DisposableEmail disposableEmail = disposableEmailRepository.findByEmailAddress(emailAddress)
                .orElseThrow(() -> new RuntimeException("Disposable email addresss not found"));
        if (disposableEmail.getExpiresAt() != null && disposableEmail.getExpiresAt().isBefore(LocalDateTime.now())) {
            log.error("Disposable email address has expired");
            throw new RuntimeException("Disposable email address has expired");
        }
        if(disposableEmail.getEmails() == null || disposableEmail.getEmails().isEmpty()){
            log.info("No emails found for the disposable email address: {}", emailAddress);
            throw new RuntimeException("No emails found for the disposable email address: "+ emailAddress);
        }
        log.info("Fetching emails..");
        return disposableEmail.getEmails().stream()
                .map(email -> {
                    EmailDTO dto = new EmailDTO();
                    dto.setId(email.getId().toString());
                    dto.setFromAddress(email.getFromAddress());
                    dto.setSubject(email.getSubject());
                    dto.setContent(email.getBody());
                    dto.setReceivedAt(email.getReceivedAt());
                    dto.setStatus(String.valueOf(email.getStatus()));
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
