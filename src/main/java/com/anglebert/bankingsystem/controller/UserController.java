package com.anglebert.bankingsystem.controller;

import com.anglebert.bankingsystem.dto.BankResponse;
import com.anglebert.bankingsystem.dto.CreditDebitRequest;
import com.anglebert.bankingsystem.dto.EnquiryRequest;
import com.anglebert.bankingsystem.dto.UserRequest;
import com.anglebert.bankingsystem.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public BankResponse createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }

    @GetMapping("/balanceEnquiry")
    public  BankResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return  userService.balanceEnquiry(enquiryRequest);
    }

    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return  userService.nameEnquiry(enquiryRequest);
    }

    @PostMapping("/credit")
    public  BankResponse creditAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return  userService.creditAccount(creditDebitRequest);
    }

    @PostMapping("/debit")
    public  BankResponse debitAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return  userService.debitAccount(creditDebitRequest);
    }
}
