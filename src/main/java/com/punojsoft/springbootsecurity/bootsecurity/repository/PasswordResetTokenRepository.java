package com.punojsoft.springbootsecurity.bootsecurity.repository;

import com.punojsoft.springbootsecurity.bootsecurity.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
}
