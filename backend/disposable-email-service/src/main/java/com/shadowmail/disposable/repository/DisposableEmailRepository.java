package com.shadowmail.disposable.repository;

import com.shadowmail.disposable.model.DisposableEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisposableEmailRepository extends JpaRepository<DisposableEmail, Long> {

    boolean existsByEmailAddress(String s);

    Optional<DisposableEmail> findByEmailAddress(String recipientEmail);

    @Query("Select e from DisposableEmail e where e.expiresAt < current_timestamp")
    Optional<List<DisposableEmail>> findAllExpiredEmail();
}
