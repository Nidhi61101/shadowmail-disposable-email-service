package com.shadowmail.disposable.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EmailContent {

    @JsonProperty("from")
    private String fromUser;

    @JsonProperty("to")
    private String toUser;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("body")
    private String body;
}
