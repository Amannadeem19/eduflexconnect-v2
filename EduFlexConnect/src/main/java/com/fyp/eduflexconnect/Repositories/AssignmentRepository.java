package com.fyp.eduflexconnect.Repositories;

import com.fyp.eduflexconnect.Models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

}