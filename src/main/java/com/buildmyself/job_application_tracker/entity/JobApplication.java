package com.buildmyself.job_application_tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "job_applications")
@Getter
@Setter
@NoArgsConstructor
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompanyCountry companyCountry;

    @Column(nullable = false)
    private String jobTitle;

    @Column(nullable = false)
    private String jobUrl;

    private Integer salaryMin;

    private Integer salaryMax;

    private Integer expectedSalary;

    private Integer offeredSalary = 0;

    private boolean sponsorsEP;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.APPLIED;

    @Column(updatable = false)
    private LocalDate appliedDate;

    @Column(length = 2000)
    private String notes;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
