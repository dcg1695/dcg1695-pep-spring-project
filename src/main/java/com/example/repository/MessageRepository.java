package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Message;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    
    @Query("SELECT m FROM Message m WHERE m.posted_by = ?1")
    List<Message> findAllByPostedBy(Integer posted_by);
}
