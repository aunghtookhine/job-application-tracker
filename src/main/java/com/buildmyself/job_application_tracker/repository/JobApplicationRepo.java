package com.buildmyself.job_application_tracker.repository;

import com.buildmyself.job_application_tracker.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepo extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findAllByUserId(long userId);
    Optional<JobApplication> findByIdAndUserId(long id, long userId);
}
