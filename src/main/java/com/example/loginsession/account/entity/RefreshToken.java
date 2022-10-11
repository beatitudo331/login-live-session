package com.example.loginsession.account.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String refreshToken;
    @NotBlank
    private String accountEmail;

    public RefreshToken() {}

    private void setRefreshToken(String token) {
        this.refreshToken = token;
    }

    private void setAccountEmail(String email) {
        this.accountEmail = email;
    }

    public RefreshToken(String token, String email) {
        setRefreshToken(token);
        setAccountEmail(email);
    }

    public RefreshToken update(String token) {
        setRefreshToken(token);
        return this;
    }
}
