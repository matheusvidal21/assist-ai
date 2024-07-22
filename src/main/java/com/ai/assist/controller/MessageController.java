package com.ai.assist.controller;

import com.ai.assist.controller.routes.Routes;
import com.ai.assist.dto.MessageDto;
import com.ai.assist.model.Message;
import com.ai.assist.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Routes.MESSAGE)
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    public ResponseEntity<List<Message>> getAll() {
        return ResponseEntity.ok(messageService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Message> getById(Long id) {
        return ResponseEntity.ok(messageService.findById(id));
    }

    public ResponseEntity<Message> save(@RequestBody @Valid MessageDto message){

    }


}
