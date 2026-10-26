package com.buildmyself.job_application_tracker.dto;

import com.buildmyself.job_application_tracker.entity.CompanyCountry;
import com.buildmyself.job_application_tracker.validator.ValueOfEnum;
import jakarta.validation.constraints.*;

public record JobApplicationRequest (
        @NotBlank String companyName,
        @NotNull @ValueOfEnum(enumClass = CompanyCountry.class) String companyCountry,
        @NotBlank String jobTitle,
        @NotBlank String jobUrl,
        @PositiveOrZero Integer salaryMin,
        @PositiveOrZero Integer salaryMax,
        @PositiveOrZero Integer expectedSalary,
        @NotNull Boolean sponsorsEP,
        @Size(max = 5000) String notes
){
    @AssertTrue(message = "SalaryMin cannot be greater than SalaryMax")
    public boolean isSalaryRangeValid() {
        return salaryMin == null || salaryMax == null || salaryMin <= salaryMax;
    }
}
