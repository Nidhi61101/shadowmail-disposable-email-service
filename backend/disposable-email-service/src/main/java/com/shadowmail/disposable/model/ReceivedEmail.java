package com.shadowmail.disposable.model;

import com.shadowmail.disposable.util.SpamStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "received_email")
public class ReceivedEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "disposable_id", nullable = false)
    private DisposableEmail disposableEmail;

    @Column(name = "from_user", nullable = false)
    private String fromAddress;

    @Column(name = "to_user", nullable = false)
    private String toAddress;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SpamStatus status;
}

