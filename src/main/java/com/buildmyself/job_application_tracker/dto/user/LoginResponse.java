package com.buildmyself.job_application_tracker.dto.user;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresInSeconds
) {
}
