package com.example.hogwarts.controller;

import com.example.hogwarts.dto.Event;
import com.example.hogwarts.dto.LeaderboardEntry;
import com.example.hogwarts.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class EventController {
    private final EventService eventService;
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/ingest/event")
    public ResponseEntity<Event> ingestEvent(@RequestBody @Valid Event event) {
        Event savedEvent = eventService.ingestEvent(event);
        return ResponseEntity.ok().body(savedEvent);
    }

    @GetMapping("/leaderboard")
    public List<LeaderboardEntry> getLeaderboard(@RequestParam(defaultValue = "all") String window) {
        return eventService.getLeaderboardEntryList(window);
    }

    @GetMapping(path = "/leaderboard/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(@RequestParam(defaultValue = "all") String window) {
        return eventService.createEmitter(window);
    }
}
