package com.shadowmail.disposable.service;

import com.shadowmail.disposable.model.DisposableEmail;
import com.shadowmail.disposable.model.ReceivedEmail;
import com.shadowmail.disposable.repository.DisposableEmailRepository;
import com.shadowmail.disposable.repository.ReceivedEmailRepository;
import com.shadowmail.disposable.util.SpamStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;

@Service
public class EmailParser {

    private static final Logger log = LoggerFactory.getLogger(EmailParser.class);

    @Autowired
    private ReceivedEmailRepository receivedEmailRepository;

    @Autowired
    private DisposableEmailRepository disposableEmailRepository;

    public void parseAndStoreEmailContent(InputStream data) {
        Session session = Session.getInstance(new Properties());
        try {
            MimeMessage message = new MimeMessage(session, data);
            ReceivedEmail receivedEmail = new ReceivedEmail();
            receivedEmail.setFromAddress(String.valueOf(message.getFrom()[0]));
            receivedEmail.setToAddress(String.valueOf(message.getAllRecipients()[0]));
            receivedEmail.setSubject(message.getSubject());
            if (message.getContent() instanceof String) {
                receivedEmail.setBody(message.getContent().toString());
            } else if (message.getContent() instanceof Multipart multipart) {
                for (int i = 0; i < multipart.getCount(); i++) {
                    BodyPart part = multipart.getBodyPart(i);
                    if (part.getContent() instanceof String) {
                        receivedEmail.setBody(part.getContent().toString());
                        break;
                    } else {
                        log.error("Unsupported content type: {}", part.getContentType());
                    }
                }
            } else {
                log.error("Email content is not supported");
            }
            log.info("Parsed email content successfully: {}", receivedEmail);
            receivedEmail.setReceivedAt(LocalDateTime.now());
            receivedEmail.setStatus(SpamStatus.PENDING);
            DisposableEmail disposableEmail = disposableEmailRepository.findByEmailAddress(receivedEmail.getToAddress())
                    .orElseThrow( ()-> new RuntimeException("Dispoable email address not found"));
            receivedEmail.setDisposableEmail(disposableEmail);
            receivedEmailRepository.save(receivedEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
