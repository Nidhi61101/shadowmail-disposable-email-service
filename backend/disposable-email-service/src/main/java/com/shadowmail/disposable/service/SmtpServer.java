package com.shadowmail.disposable.service;

import com.shadowmail.disposable.repository.DisposableEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.subethamail.smtp.server.SMTPServer;
import org.subethamail.smtp.helper.SimpleMessageListener;
import org.subethamail.smtp.helper.SimpleMessageListenerAdapter;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class SmtpServer {

    private final EmailParser emailParser;
    private final DisposableEmailRepository disposableEmailRepository;
    private SMTPServer smtpServer;

    public SmtpServer(EmailParser emailParser, DisposableEmailRepository disposableEmailRepository) {
        this.emailParser = emailParser;
        this.disposableEmailRepository = disposableEmailRepository;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        smtpServer = new SMTPServer(new SimpleMessageListenerAdapter(new EmailListener(disposableEmailRepository, emailParser)));
        smtpServer.setPort(2525);
        smtpServer.start();
        System.out.println("SMTP server started on port 2525");
    }

    static class EmailListener implements SimpleMessageListener {

        private final DisposableEmailRepository disposableEmailRepository;
        private final EmailParser emailParser;

        public EmailListener(DisposableEmailRepository disposableEmailRepository, EmailParser emailParser) {
            this.disposableEmailRepository = disposableEmailRepository;
            this.emailParser = emailParser;
        }

        @Override
        public boolean accept(String from, String recipient) {
            return (null != recipient &&
                    recipient.endsWith("@shadowmail.com") &&
                    disposableEmailRepository.existsByEmailAddress(recipient));
        }

        @Override
        public void deliver(String from, String recipient, InputStream data) {
            System.out.println("Received email from: " + from + " to: " + recipient);
            emailParser.parseAndStoreEmailContent(data);
        }
    }
}
