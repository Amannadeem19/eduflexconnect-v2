package com.fyp.eduflexconnect.Repositories;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Models.Society;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocietyRepository extends JpaRepository<Society,Long> {
    @Query("SELECT DISTINCT s from Society s where s.title LIKE %:query% OR s.type LIKE %:query% OR s.societyName LIKE %:query%")
    public List<Society> searchSocietyPosts(@Param("query") String query) throws AnnouncementException;

}
