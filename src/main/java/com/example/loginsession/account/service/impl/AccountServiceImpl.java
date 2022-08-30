package com.example.loginsession.account.service.impl;

import com.example.loginsession.account.dto.AccountReqDto;
import com.example.loginsession.account.dto.LoginReqDot;
import com.example.loginsession.account.entity.Account;
import com.example.loginsession.account.entity.RefreshToken;
import com.example.loginsession.account.repository.AccountRepository;
import com.example.loginsession.account.repository.RefreshTokenRepository;
import com.example.loginsession.account.service.AccountService;
import com.example.loginsession.global.dto.GlobalResDto;
import com.example.loginsession.jwt.dto.TokenDto;
import com.example.loginsession.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@Primary
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public GlobalResDto signup(AccountReqDto accountReqDto) {
        // email 중복 검사
        if (accountRepository.findByEmail(accountReqDto.getEmail()).isPresent()) {
            throw new RuntimeException("SignUp Fail Cause Overlap");
        }

        accountReqDto.setPassword(passwordEncoder.encode(accountReqDto.getPassword()));
        Account account = new Account(accountReqDto);
        accountRepository.save(account);

        return new GlobalResDto("Signup Success", HttpStatus.OK.value());
    }

    @Override
    @Transactional
    public GlobalResDto login(LoginReqDot loginReqDot, HttpServletResponse response) {

        Account account = accountRepository.findByEmail(loginReqDot.getEmail()).orElseThrow(
                ()->new RuntimeException("Not Find Account")
        );

        if(!passwordEncoder.matches(loginReqDot.getPassword(), account.getPassword())){
            throw new RuntimeException("Not Match Password");
        }

        // 토큰 발급
        TokenDto tokenDto = jwtUtil.createAllToken(account.getEmail());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountEmail(account.getEmail());

        if(refreshToken.isPresent()) {
            refreshToken.get().setRefreshToken(tokenDto.getRefreshToken());
            refreshTokenRepository.save(refreshToken.get());
        }else{
            RefreshToken newRefreshToken = new RefreshToken(tokenDto.getRefreshToken(), account.getEmail());
            refreshTokenRepository.save(newRefreshToken);
        }

        setTokenOnHeader(response, tokenDto);

        return new GlobalResDto("Login Success", HttpStatus.OK.value());
    }

    private void setTokenOnHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }


}
