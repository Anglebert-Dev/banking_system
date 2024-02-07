package com.anglebert.bankingsystem.repository;

import com.anglebert.bankingsystem.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,String>{

}
