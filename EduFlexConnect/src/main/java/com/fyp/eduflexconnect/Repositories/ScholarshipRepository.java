package com.fyp.eduflexconnect.Repositories;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Models.Scholarship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScholarshipRepository extends JpaRepository<Scholarship, Long> {
    @Query("SELECT DISTINCT s from Scholarship s where s.title LIKE %:query% OR s.provider LIKE %:query%")
    public List<Scholarship> searchScholarships(@Param("query") String query) throws AnnouncementException;

}
