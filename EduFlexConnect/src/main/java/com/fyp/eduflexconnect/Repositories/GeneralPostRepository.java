package com.fyp.eduflexconnect.Repositories;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Models.GeneralPosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneralPostRepository extends JpaRepository<GeneralPosts, Long> {

    @Query("SELECT DISTINCT j from GeneralPosts j where j.title LIKE %:query% OR j.type LIKE %:query%")
    public List<GeneralPosts> searchPosts(@Param("query") String query) throws AnnouncementException;
}
