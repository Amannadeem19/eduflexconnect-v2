package com.fyp.eduflexconnect.Repositories;

import com.fyp.eduflexconnect.Models.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SemesterRepository extends JpaRepository<Semester,String>
{
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Semester s WHERE s.status = 'Current'")
    boolean existsByStatusIsCurrent();

    @Query("SELECT s.semester_id FROM Semester s WHERE s.status = 'Current'")
    String currentSemesterId();



}
