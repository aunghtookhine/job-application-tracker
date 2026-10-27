package com.buildmyself.job_application_tracker.business;

import com.buildmyself.job_application_tracker.config.security.AppUserDetails;
import com.buildmyself.job_application_tracker.config.security.JwtService;
import com.buildmyself.job_application_tracker.dto.user.LoginRequest;
import com.buildmyself.job_application_tracker.dto.user.LoginResponse;
import com.buildmyself.job_application_tracker.dto.user.RegisterRequest;
import com.buildmyself.job_application_tracker.dto.user.RegisterResponse;
import com.buildmyself.job_application_tracker.entity.User;
import com.buildmyself.job_application_tracker.exception.BusinessException;
import com.buildmyself.job_application_tracker.exception.ErrorCode;
import com.buildmyself.job_application_tracker.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public RegisterResponse register(RegisterRequest registerRequest){
        String email = registerRequest.email().trim().toLowerCase();
        boolean existingUser = userRepo.existsByEmail(email);
        if (existingUser){
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_REGISTERED);
        }

        User user = new User();
        user.setEmail(email);
        user.setFullName(registerRequest.fullName());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.password()));
        User newUser = userRepo.save(user);

        return RegisterResponse.from(newUser);
    }

    public LoginResponse login(LoginRequest loginRequest){
        String email = loginRequest.email().trim().toLowerCase();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken.unauthenticated(email, loginRequest.password()));
            AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
            return jwtService.generate(userDetails.id(), userDetails.getUsername());
        } catch (AuthenticationException e) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
        }
    }
}
