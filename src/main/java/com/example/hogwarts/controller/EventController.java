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

    /**
     * Ingests a new event into the system.
     *
     * @param event the event payload received from the client
     * @return ResponseEntity containing the saved event
     */
    @PostMapping("/ingest/event")
    public ResponseEntity<Event> ingestEvent(@RequestBody @Valid Event event) {
        Event savedEvent = eventService.ingestEvent(event);
        return ResponseEntity.ok().body(savedEvent);
    }

    /**
     * Retrieves the leaderboard entries based on the requested time window.
     *
     * @param window the time window for leaderboard aggregation ("all", "5m", "1h")
     * @return a list of leaderboard entries sorted by points in descending order
     */
    @GetMapping("/leaderboard")
    public List<LeaderboardEntry> getLeaderboard(@RequestParam(defaultValue = "all") String window) {
        return eventService.getLeaderboardEntryList(window);
    }

    /**
     * Creates a server-sent events (SSE) stream for leaderboard updates.
     *
     * @param window the time window for leaderboard aggregation ("all", "5m", "1h")
     * @return a configured {@link SseEmitter} that streams leaderboard updates
     */
    @GetMapping(path = "/leaderboard/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(@RequestParam(defaultValue = "all") String window) {
        return eventService.createEmitter(window);
    }
}
