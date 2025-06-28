package com.shadowmail.disposable.util;

import com.shadowmail.disposable.model.DisposableEmail;
import com.shadowmail.disposable.repository.DisposableEmailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DisposableEmailExpiryScheduler {

    private final DisposableEmailRepository disposableEmailRepository;

    @EventListener
    @Transactional
    public void onStartup(org.springframework.context.event.ContextRefreshedEvent event) {
        log.info("Checking for expired disposable emails on startup..");

        Optional<List<DisposableEmail>> expiredEmails = disposableEmailRepository.findAllExpiredEmail();

        if (expiredEmails.isEmpty()) {
            log.info("No expired disposable emails found on startup.");
            return;
        }

        for (DisposableEmail email : expiredEmails.get()) {
            email.setActive(false);
        }

        disposableEmailRepository.saveAll(expiredEmails.get());

        log.info("Expired {} disposable email(s) on startup.", expiredEmails.get().size());
    }
}
