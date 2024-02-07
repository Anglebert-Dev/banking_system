package com.anglebert.bankingsystem.service.impl;

import com.anglebert.bankingsystem.dto.BankResponse;
import com.anglebert.bankingsystem.dto.CreditDebitRequest;
import com.anglebert.bankingsystem.dto.EnquiryRequest;
import com.anglebert.bankingsystem.dto.UserRequest;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
    String nameEnquiry(EnquiryRequest enquiryRequest);

    BankResponse creditAccount(CreditDebitRequest creditRequest);
    BankResponse debitAccount(CreditDebitRequest creditRequest);

}
