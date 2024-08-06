package com.ai.assist.controller;

import com.ai.assist.controller.routes.Routes;
import com.ai.assist.dto.AssignTicketDto;
import com.ai.assist.dto.TicketDto;
import com.ai.assist.dto.response.TicketResponse;
import com.ai.assist.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routes.TICKETS)
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_SUPPORT')")
    public ResponseEntity<List<TicketResponse>> getAll(){
        return ResponseEntity.ok(this.ticketService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_SUPPORT')")
    public ResponseEntity<TicketResponse> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.ticketService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_USER', 'SCOPE_SUPPORT')")
    public ResponseEntity<TicketResponse> save(@RequestBody @Valid TicketDto ticketDto){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(this.ticketService.create(ticketDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_SUPPORT')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        this.ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_SUPPORT')")
    public ResponseEntity<List<TicketResponse>> getAllByUserId(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.ticketService.findByUserId(id));
    }

    @GetMapping("/assigned-to/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_SUPPORT')")
    public ResponseEntity<List<TicketResponse>> getAllByAssignedTo(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.ticketService.findByAssignedTo(id));
    }

    @PostMapping("/assign-to")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_SUPPORT')")
    public ResponseEntity<TicketResponse> assignTo(@RequestBody @Valid AssignTicketDto assignTicketDto){
        return ResponseEntity.ok(this.ticketService.assignTo(assignTicketDto.getTicketId(), assignTicketDto.getAssignedTo()));
    }


}
