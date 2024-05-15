package com.fyp.eduflexconnect.Repositories;


import com.fyp.eduflexconnect.Models.Assignment;
import com.fyp.eduflexconnect.Models.FilesEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilesRepository extends JpaRepository<FilesEntity, Long> {
    @Query("SELECT f FROM FilesEntity f WHERE f.content.id = :content_id")
    public List<FilesEntity> findByContentId(@Param("content_id") Long content_id);
    @Transactional
    @Modifying
    @Query("DELETE FROM FilesEntity f WHERE f.content.id = :content_id")
    public void deleteByContentId(@Param("content_id")Long content_id);

    public FilesEntity findByJobApplicantId(Long id);

    public  FilesEntity findByAssignment(Assignment assignment);

//    public FilesEntity findById(String name);
}
