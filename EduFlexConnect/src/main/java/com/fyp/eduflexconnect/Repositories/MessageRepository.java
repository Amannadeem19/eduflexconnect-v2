package com.fyp.eduflexconnect.Repositories;

import com.fyp.eduflexconnect.Models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("Select m from Message m join m.chat c where c.id =: chatId")
    public List<Message> findByChatId(@Param("chatId") Long chatId);
}
