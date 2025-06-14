package com.shadowmail.disposable.service;

import com.shadowmail.disposable.model.EmailContent;
import com.shadowmail.disposable.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailReceiverService {

    @Autowired
    private EmailRepository emailRepository;

    public void receiveEmail(EmailContent emailContent){
        String receiptentEmail = emailContent.getToUser();

    }
}
