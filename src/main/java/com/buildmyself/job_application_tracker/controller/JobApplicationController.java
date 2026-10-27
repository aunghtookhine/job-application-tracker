package com.buildmyself.job_application_tracker.controller;

import com.buildmyself.job_application_tracker.business.JobApplicationService;
import com.buildmyself.job_application_tracker.dto.CommonResponse;
import com.buildmyself.job_application_tracker.dto.job_application.JobApplicationRequest;
import com.buildmyself.job_application_tracker.dto.job_application.JobApplicationResponse;
import com.buildmyself.job_application_tracker.dto.user.AuthUser;
import com.buildmyself.job_application_tracker.entity.ApplicationStatus;
import com.buildmyself.job_application_tracker.validator.ValueOfEnum;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @GetMapping
    public CommonResponse<List<JobApplicationResponse>> getAll(
            @AuthenticationPrincipal AuthUser authUser
    ) {
        return CommonResponse.success(jobApplicationService.findAll(authUser));
    }

    @GetMapping("/{id}")
    public CommonResponse<JobApplicationResponse> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal AuthUser authUser) {
        return CommonResponse.success(jobApplicationService.findById(id, authUser));
    }

    @PostMapping
    public CommonResponse<JobApplicationResponse> create(
            @RequestBody @Valid JobApplicationRequest jobApplicationRequest,
            @AuthenticationPrincipal AuthUser authUser) {
        return CommonResponse.success(jobApplicationService.create(jobApplicationRequest, authUser));
    }

    @PatchMapping("/{id}")
    public CommonResponse<JobApplicationResponse> updateStatus(
            @RequestParam @ValueOfEnum(enumClass = ApplicationStatus.class) String status,
            @PathVariable Long id,
            @AuthenticationPrincipal AuthUser authUser) {
        return CommonResponse.success(jobApplicationService.updateStatus(status, id, authUser));
    }
}
