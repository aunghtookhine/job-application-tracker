package com.buildmyself.job_application_tracker.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest (
        @NotBlank @Email String email,
        @NotBlank String password
){
}
