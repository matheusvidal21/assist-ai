
package com.ai.assist.service.impl;

import com.ai.assist.exception.NotFoundException;
import com.ai.assist.model.Message;
import com.ai.assist.model.Ticket;
import com.ai.assist.model.User;
import com.ai.assist.repository.MessageRepository;
import com.ai.assist.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<Message> findAll() {
        return this.messageRepository.findAll();
    }

    @Override
    public Message findById(Long id) {
        return this.messageRepository.findById(id).orElseThrow(() -> new NotFoundException("Message not found"));
    }

    @Override
    public Message save(Message message) {
        return this.messageRepository.save(message);
    }

    @Override
    public void delete(Long id) {
        this.messageRepository.deleteById(id);
    }
}
