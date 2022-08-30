package com.example.loginsession.account.entity;

import com.example.loginsession.account.dto.AccountReqDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String phoneNumber;
    private Long kakaoId;

    public Account() {}

    public Account(AccountReqDto accountReqDto) {
        this.email = accountReqDto.getEmail();
        this.password = accountReqDto.getPassword();
        this.phoneNumber = accountReqDto.getPhoneNumber();
    }

    public Account(String encodedPassword, String email, Long kakaoId) {
        this.password = encodedPassword;
        this.email = email;
        this.kakaoId = kakaoId;
    }
}
