package com.anglebert.bankingsystem.service.impl;

import com.anglebert.bankingsystem.dto.BankResponse;
import com.anglebert.bankingsystem.dto.UserRequest;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
}
