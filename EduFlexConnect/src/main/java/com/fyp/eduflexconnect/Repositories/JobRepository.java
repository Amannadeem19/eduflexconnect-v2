package com.fyp.eduflexconnect.Repositories;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("SELECT DISTINCT j from Job j where j.title LIKE %:query% OR j.companyName LIKE %:query%")
    public List<Job> searchJobs(@Param("query") String query) throws AnnouncementException;

}
