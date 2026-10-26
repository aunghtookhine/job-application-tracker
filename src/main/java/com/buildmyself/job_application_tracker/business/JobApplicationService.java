package com.buildmyself.job_application_tracker.business;

import com.buildmyself.job_application_tracker.dto.JobApplicationRequest;
import com.buildmyself.job_application_tracker.dto.JobApplicationResponse;
import com.buildmyself.job_application_tracker.entity.ApplicationStatus;
import com.buildmyself.job_application_tracker.entity.CompanyCountry;
import com.buildmyself.job_application_tracker.entity.JobApplication;
import com.buildmyself.job_application_tracker.exception.BusinessException;
import com.buildmyself.job_application_tracker.exception.ErrorCode;
import com.buildmyself.job_application_tracker.repository.JobApplicationRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobApplicationService {
    private final JobApplicationRepo jobApplicationRepo;

    public JobApplicationService(JobApplicationRepo jobApplicationRepo) {
        this.jobApplicationRepo = jobApplicationRepo;
    }

    public List<JobApplicationResponse> findAll() {
        List<JobApplication> jobApplicationList = jobApplicationRepo.findAll();
        return jobApplicationList.stream().map(JobApplicationResponse::from).toList();
    }

    public JobApplicationResponse findById(Long id) {
        return JobApplicationResponse.from(
                jobApplicationRepo.findById(id).orElseThrow(
                        () -> new BusinessException(ErrorCode.INVALID_APPLICATION_ID)));
    }

    @Transactional
    public JobApplicationResponse create(JobApplicationRequest jobApplicationRequest) {
        JobApplication jobApplication = new JobApplication();
        jobApplication.setCompanyName(jobApplicationRequest.companyName());
        jobApplication.setCompanyCountry(CompanyCountry.valueOf(jobApplicationRequest.companyCountry()));
        jobApplication.setJobTitle(jobApplicationRequest.jobTitle());
        jobApplication.setJobUrl(jobApplicationRequest.jobUrl());
        jobApplication.setSalaryMin(jobApplicationRequest.salaryMin() == null ? 0 : jobApplicationRequest.salaryMin());
        jobApplication.setSalaryMax(jobApplicationRequest.salaryMax() == null ? 0 : jobApplicationRequest.salaryMax());
        jobApplication.setExpectedSalary(jobApplicationRequest.expectedSalary() == null ? 0 : jobApplicationRequest.expectedSalary());
        jobApplication.setSponsorsEP(jobApplicationRequest.sponsorsEP());
        jobApplication.setAppliedDate(LocalDate.now());
        jobApplication.setNotes(jobApplicationRequest.notes());

        JobApplication newJobApplication = jobApplicationRepo.save(jobApplication);

        return JobApplicationResponse.from(newJobApplication);
    }

    @Transactional
    public JobApplicationResponse updateStatus(String applicationStatus, Long id) {
        JobApplication jobApplication = jobApplicationRepo.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_APPLICATION_ID));

        jobApplication.setStatus(ApplicationStatus.valueOf(applicationStatus));

        return JobApplicationResponse.from(jobApplicationRepo.save(jobApplication));
    }
}
