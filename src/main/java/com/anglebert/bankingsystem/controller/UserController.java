package com.anglebert.bankingsystem.controller;

import com.anglebert.bankingsystem.dto.*;
import com.anglebert.bankingsystem.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/user")
@Tag(name = "User Account Management APIs")
public class UserController {
    @Autowired
    UserService userService;

    @Operation(
            summary = "CREATE NEW USER ACCOUNT",
            description = "creating new user account "
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 CREATED"
    )
    @PostMapping
    public BankResponse createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }

    @PostMapping("/login")
    public BankResponse login(@RequestBody LoginDto loginDto){
        return userService.login(loginDto);
    }

    @Operation(
            summary = "BALANCE ENQUIRY",
            description = "Check user's account Balance with Account number "
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @GetMapping("/balanceEnquiry")
    public  BankResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return  userService.balanceEnquiry(enquiryRequest);
    }

    @Operation(
            summary = "NAME ENQUIRY",
            description = "Check user's account Name  with Account number "
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return  userService.nameEnquiry(enquiryRequest);
    }

    @Operation(
            summary = "CREDIT ACCOUNT",
            description = "Deposit Money On User Account "
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 CREATED"
    )
    @PostMapping("/credit")
    public  BankResponse creditAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return  userService.creditAccount(creditDebitRequest);
    }

    @Operation(
            summary = "DEBIT ACCOUNT",
            description = "Withdraw Money On User Account "
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 CREATED"
    )
    @PostMapping("/debit")
    public  BankResponse debitAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return  userService.debitAccount(creditDebitRequest);
    }
    @Operation(
            summary = "TRANSFER MONEY",
            description = "Send Money from one Account to another  "
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 CREATED"
    )
    @PostMapping("/transfer")
    public  BankResponse transfer(@RequestBody TransferRequest request){
        return  userService.transfer(request);
    }
}
