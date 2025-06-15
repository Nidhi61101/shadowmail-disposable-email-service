package com.shadowmail.disposable.service;

import com.shadowmail.disposable.model.DisposableEmail;
import com.shadowmail.disposable.repository.DisposableEmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

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
        log.info("Created new disposable email address: {}",emailAddress);
        return email;
    }

    private String generateEmailAddress() {
        String domain = "@shadowmail.com";
        String emailAddress;
        do {
            emailAddress = UUID.randomUUID().toString().substring(0, 8) + domain;
        } while (disposableEmailRepository.existsByEmailAddress(emailAddress));
        log.info("Generated new disposable email address: {}",emailAddress);
        return emailAddress;
    }
}
