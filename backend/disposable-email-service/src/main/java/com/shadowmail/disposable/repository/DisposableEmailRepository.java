package com.shadowmail.disposable.repository;

import com.shadowmail.disposable.model.DisposableEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisposableEmailRepository extends JpaRepository<DisposableEmail, Long> {

    boolean existsByEmailAddress(String s);
}
