package com.akash.blog.service;

import com.akash.blog.payload.LoginDto;
import com.akash.blog.payload.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);

}
