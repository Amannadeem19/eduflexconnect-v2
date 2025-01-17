package com.fyp.eduflexconnect.Repositories;


import com.fyp.eduflexconnect.Models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.announcement.id = :announce_id")
    public List<Comment> findByAnnouncementId(@Param("announce_id") Long announce_id);
}
