package com.buildmyself.job_application_tracker.controller;

import com.buildmyself.job_application_tracker.business.JobApplicationService;
import com.buildmyself.job_application_tracker.dto.CommonResponse;
import com.buildmyself.job_application_tracker.dto.JobApplicationRequest;
import com.buildmyself.job_application_tracker.dto.JobApplicationResponse;
import com.buildmyself.job_application_tracker.entity.ApplicationStatus;
import com.buildmyself.job_application_tracker.validator.ValueOfEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    public CommonResponse<List<JobApplicationResponse>> getAll() {
        return CommonResponse.success(jobApplicationService.findAll());
    }

    @GetMapping("/{id}")
    public CommonResponse<JobApplicationResponse> getById(@PathVariable Long id) {
        return CommonResponse.success(jobApplicationService.findById(id));
    }

    @PostMapping
    public CommonResponse<JobApplicationResponse> create(@RequestBody @Valid JobApplicationRequest jobApplicationRequest) {
        return CommonResponse.success(jobApplicationService.create(jobApplicationRequest));
    }

    @PatchMapping("/{id}")
    public CommonResponse<JobApplicationResponse> updateStatus(
            @RequestParam @ValueOfEnum(enumClass = ApplicationStatus.class) String status,
            @PathVariable Long id) {
        return CommonResponse.success(jobApplicationService.updateStatus(status, id));
    }
}
