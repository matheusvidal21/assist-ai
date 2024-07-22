package com.ai.assist.controller;

import com.ai.assist.controller.routes.Routes;
import com.ai.assist.dto.TicketDto;
import com.ai.assist.model.Ticket;
import com.ai.assist.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routes.TICKET)
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<Ticket>> getAll(){
        return ResponseEntity.ok(this.ticketService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.ticketService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Ticket> save(@RequestBody @Valid TicketDto ticketDto){
        return ResponseEntity.ok(this.ticketService.create(ticketDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        this.ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Ticket>> getAllByUserId(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.ticketService.findByUserId(id));
    }

    @GetMapping("/assigned-to/{id}")
    public ResponseEntity<List<Ticket>> getAllByAssignedTo(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.ticketService.findByAssignedTo(id));
    }

}
