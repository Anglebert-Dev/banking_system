package com.anglebert.bankingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String firstName;
    private String lastName;
    private String otherName;
    private String email;
    private String phoneNumber;
    private String alternativePhoneNumber;
    private String gender;
    private String address;
    private String stateOfOrigin;
}
