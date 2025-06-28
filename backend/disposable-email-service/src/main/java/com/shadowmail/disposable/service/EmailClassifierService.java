package com.shadowmail.disposable.service;

import com.shadowmail.disposable.util.SpamStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailClassifierService {

    public static final String SPAM = "spam";
    public static final String HAM = "ham";
    public static final String RESULT = "result";
    private static final Logger log = LoggerFactory.getLogger(EmailClassifierService.class);
    private static final String URL = "http://localhost:5000/predict";
    @Autowired
    private RestTemplate restTemplate;

    public SpamStatus classifyText(String text) {
        SpamStatus spamStatus = SpamStatus.PENDING;
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("text", text);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        try {
            log.info("Classifying text: {}", text);
            ResponseEntity<Map> response = restTemplate.postForEntity(URL, requestEntity, Map.class);
            String result = (String) response.getBody().get(RESULT);

            log.info("Received classificatiion result: {}", result);
            if (SPAM.equalsIgnoreCase(result)) {
                spamStatus = SpamStatus.SPAM;
            } else if (HAM.equalsIgnoreCase((result))) {
                spamStatus = SpamStatus.HAM;
            }
        } catch (Exception e) {
            log.error("Error classifying text: {}", e.getMessage());
        }
        return spamStatus;
    }

}
