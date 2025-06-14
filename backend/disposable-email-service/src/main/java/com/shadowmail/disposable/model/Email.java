package com.shadowmail.disposable.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "emails")
public class Email {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "disposable_id", nullable = false)
    private DisposableEmail disposableEmail;

    @Column(name = "from_address", nullable = false)
    private String fromAddress;

    @Column(name = "subject")
    private String subject;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt;

}
