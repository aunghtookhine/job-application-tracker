package com.buildmyself.job_application_tracker.repository;

import com.buildmyself.job_application_tracker.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepo extends JpaRepository<JobApplication, Long> {
}
