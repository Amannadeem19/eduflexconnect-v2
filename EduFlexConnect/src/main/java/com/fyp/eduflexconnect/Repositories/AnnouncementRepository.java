package com.fyp.eduflexconnect.Repositories;


import com.fyp.eduflexconnect.Models.Announcement;
import com.fyp.eduflexconnect.Models.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    public List<Announcement> findAllByOrderByCreatedAtDesc();

    public List<Announcement> findByClassroom(Classroom classroom);

}
