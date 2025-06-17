package com.shadowmail.disposable.service;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.subethamail.smtp.server.SMTPServer;
import org.subethamail.smtp.helper.SimpleMessageListener;
import org.subethamail.smtp.helper.SimpleMessageListenerAdapter;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class SmtpServer {

    private SMTPServer smtpServer;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        smtpServer = new SMTPServer(new SimpleMessageListenerAdapter(new EmailListener()));
        smtpServer.setPort(2525);
        smtpServer.start();
        System.out.println("SMTP server started on port 2525");
    }

    static class EmailListener implements SimpleMessageListener {
        @Override
        public boolean accept(String from, String recipient) {
            return recipient.endsWith("@shadowmail.io");
        }

        @Override
        public void deliver(String from, String recipient, InputStream data) {

            System.out.println("Received email from: " + from + " to: " + recipient);
        }
    }
}
