package com.anglebert.bankingsystem.service.impl;

import com.anglebert.bankingsystem.dto.TransactionDto;

public interface TransactionService {
    void saveTransaction(TransactionDto transactionDto);
}
