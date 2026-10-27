package com.buildmyself.job_application_tracker.config.security;

import com.buildmyself.job_application_tracker.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record AppUserDetails(
        Long id,
        String email,
        String passwordHash
) implements UserDetails {

    public static AppUserDetails from(User user) {
        return new AppUserDetails(user.getId(), user.getEmail(), user.getPasswordHash());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
