package com.buildmyself.job_application_tracker.dto.user;

import com.buildmyself.job_application_tracker.entity.User;

public record RegisterResponse(
        Long id,
        String email,
        String fullName
) {
    public static RegisterResponse from(User user) {
        return new RegisterResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName());
    }
}
