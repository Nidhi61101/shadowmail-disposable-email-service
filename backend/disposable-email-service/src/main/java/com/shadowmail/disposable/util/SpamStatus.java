package com.shadowmail.disposable.util;

public enum SpamStatus {
    PENDING,   // Received but not classified yet
    SPAM,      // Classified as spam
    HAM        // Classified as not spam (i.e., safe)
}
