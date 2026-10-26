package com.buildmyself.job_application_tracker.dto;

import com.buildmyself.job_application_tracker.entity.ApplicationStatus;
import com.buildmyself.job_application_tracker.entity.CompanyCountry;
import com.buildmyself.job_application_tracker.entity.JobApplication;

import java.time.LocalDate;

public record JobApplicationResponse(
        Long id,
        String companyName,
        CompanyCountry companyCountry,
        String jobTitle,
        String jobUrl,
        Integer salaryMin,
        Integer salaryMax,
        Integer expectedSalary,
        Integer salaryOffered,
        boolean sponsorsEP,
        ApplicationStatus status,
        LocalDate appliedDate,
        String notes
) {
   public static JobApplicationResponse from(JobApplication jobApplication) {
       return new JobApplicationResponse(
               jobApplication.getId(),
               jobApplication.getCompanyName(),
               jobApplication.getCompanyCountry(),
               jobApplication.getJobTitle(),
               jobApplication.getJobUrl(),
               jobApplication.getSalaryMin(),
               jobApplication.getSalaryMax(),
               jobApplication.getOfferedSalary(),
               jobApplication.getExpectedSalary(),
               jobApplication.isSponsorsEP(),
               jobApplication.getStatus(),
               jobApplication.getAppliedDate(),
               jobApplication.getNotes()
       );
   }
}
