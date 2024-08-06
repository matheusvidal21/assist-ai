package com.ai.assist.controller;

import com.ai.assist.controller.routes.Routes;
import com.ai.assist.dto.MessageDto;
import com.ai.assist.dto.response.MessageResponse;
import com.ai.assist.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routes.MESSAGES)
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<MessageResponse>> getAll() {
        return ResponseEntity.ok(this.messageService.findAll());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<MessageResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.messageService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_USER', 'SCOPE_SUPPORT')")
    public ResponseEntity<MessageResponse> save(@RequestBody @Valid MessageDto message){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(this.messageService.create(message));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        this.messageService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ticket/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_SUPPORT')")
    public ResponseEntity<List<MessageResponse>> getAllByTicket(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.messageService.findByTicket(id));
    }

    @GetMapping("/from-user/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<MessageResponse>> getAllByFromUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.messageService.findByFromUser(id));
    }

    @GetMapping("/to-user/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<MessageResponse>> getAllByToUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.messageService.findByToUser(id));
    }

}
