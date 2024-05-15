package com.fyp.eduflexconnect.Repositories;


import com.fyp.eduflexconnect.Models.JobApplicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantJobRepository extends JpaRepository<JobApplicant, Long> {

    public List<JobApplicant> findByJobId(Long job_id);

}
