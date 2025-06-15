package com.shadowmail.disposable.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailDTO {
    private String id;
    private String fromAddress;
    private String subject;
    private String content;
    private LocalDateTime receivedAt;
}

