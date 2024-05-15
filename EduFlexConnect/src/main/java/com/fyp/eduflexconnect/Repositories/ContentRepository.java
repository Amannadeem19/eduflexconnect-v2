package com.fyp.eduflexconnect.Repositories;


import com.fyp.eduflexconnect.Models.Classroom;
import com.fyp.eduflexconnect.Models.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
//    public Content findByName(String name);
    public List<Content> findByClassroom(Classroom classroom);
}
