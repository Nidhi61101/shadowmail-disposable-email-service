package com.shadowmail.disposable.repository;

import com.shadowmail.disposable.model.DisposableEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisposableEmailRepository extends JpaRepository<DisposableEmail, Long> {

    boolean existsByEmailAddress(String s);

    Optional<DisposableEmail> findByEmailAddress(String receiptentEmail);
}
