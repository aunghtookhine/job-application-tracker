package com.buildmyself.job_application_tracker.controller;

import com.buildmyself.job_application_tracker.business.AuthService;
import com.buildmyself.job_application_tracker.dto.CommonResponse;
import com.buildmyself.job_application_tracker.dto.user.LoginResponse;
import com.buildmyself.job_application_tracker.dto.user.LoginRequest;
import com.buildmyself.job_application_tracker.dto.user.RegisterRequest;
import com.buildmyself.job_application_tracker.dto.user.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public CommonResponse<RegisterResponse> registerUser(@RequestBody @Valid RegisterRequest registerRequest) {
        return CommonResponse.success(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public CommonResponse<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return CommonResponse.success(authService.login(loginRequest));
    }
}
