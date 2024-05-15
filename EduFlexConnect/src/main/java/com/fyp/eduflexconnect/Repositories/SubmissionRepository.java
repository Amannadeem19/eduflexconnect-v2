package com.fyp.eduflexconnect.Repositories;

import com.fyp.eduflexconnect.Models.Assignment;
import com.fyp.eduflexconnect.Models.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
        List<Submission> findByAssignment(Assignment assignment);
}
