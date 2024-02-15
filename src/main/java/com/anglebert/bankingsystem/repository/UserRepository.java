package com.anglebert.bankingsystem.repository;


import com.anglebert.bankingsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByAccountNumber(String accountNumber);

    UserEntity findByAccountNumber(String accountNumber);
}
