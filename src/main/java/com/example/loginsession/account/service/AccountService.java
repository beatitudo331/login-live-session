package com.example.loginsession.account.service;

import com.example.loginsession.account.dto.AccountReqDto;
import com.example.loginsession.account.dto.LoginReqDto;
import com.example.loginsession.global.dto.GlobalResDto;

import javax.servlet.http.HttpServletResponse;

public interface AccountService {
    GlobalResDto signup(AccountReqDto accountReqDto);
    GlobalResDto login(LoginReqDto loginReqDto, HttpServletResponse response);
}
