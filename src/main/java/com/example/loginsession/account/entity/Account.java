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

    private void setEmail(String email) {
        this.email = email;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private void setKakaoId(Long id) {
        this.kakaoId = id;
    }

    public Account(AccountReqDto accountReqDto) {
        setEmail(accountReqDto.getEmail());
        setPassword(accountReqDto.getPassword());
        setPhoneNumber(accountReqDto.getPhoneNumber());
    }

    public Account(String encodedPassword, String email, Long kakaoId) {
        setPassword(encodedPassword);
        setEmail(email);
        setKakaoId(kakaoId);
    }

    public Account kakaoIdUpdate(Long kakaoId) {
        setKakaoId(kakaoId);
        return this;
    }
}
