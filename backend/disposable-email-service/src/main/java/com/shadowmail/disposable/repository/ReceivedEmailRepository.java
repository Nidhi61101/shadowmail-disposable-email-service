package com.shadowmail.disposable.repository;

import com.shadowmail.disposable.model.ReceivedEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ReceivedEmailRepository extends JpaRepository<ReceivedEmail, BigDecimal> {
}
