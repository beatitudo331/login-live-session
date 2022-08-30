package com.example.loginsession.account.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AccountReqDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String phoneNumber;
}
