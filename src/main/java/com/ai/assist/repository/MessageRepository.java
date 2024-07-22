package com.ai.assist.repository;

import com.ai.assist.model.Message;
import com.ai.assist.model.Ticket;
import com.ai.assist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByTicketId(Long ticketId);

    List<Message> findByFromUserId(Long fromUserId);

    List<Message> findByToUserId(Long toUserId);

}
