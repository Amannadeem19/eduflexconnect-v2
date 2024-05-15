package com.fyp.eduflexconnect.Repositories;

import com.fyp.eduflexconnect.Models.Chat;
import com.fyp.eduflexconnect.Models.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    boolean existsByClassroom(Classroom classroom);

   Optional <Chat> findByClassroom(Classroom classroom);

}
