package com.example.hogwarts.service;

import com.example.hogwarts.dto.Event;
import com.example.hogwarts.dto.LeaderboardEntry;
import com.example.hogwarts.dto.SSESubscriber;
import com.example.hogwarts.model.EventEntity;
import com.example.hogwarts.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    private final List<SSESubscriber> subscribers = new CopyOnWriteArrayList<>();

    public Event ingestEvent(Event event) {
        EventEntity eventEntity = new EventEntity(
                event.getId(), event.getCategory(), event.getPoints(), event.getTimestamp());
        EventEntity savedEntity = eventRepository.save(eventEntity);
        broadcastUpdate();
        return Event.fromEntity(savedEntity);
    }

    private void broadcastUpdate() {
        for (SSESubscriber sub : subscribers) {
            try {
                List<LeaderboardEntry> leaderboardEntryList = getLeaderboardEntryList(sub.getWindowKey());
                sub.getEmitter().send(SseEmitter.event()
                        .name("update")
                        .data(leaderboardEntryList));
            } catch (Exception ex) {
                subscribers.remove(sub);
            }
        }
    }

    public List<LeaderboardEntry> getLeaderboardEntryList(String windowKey) {
        Instant since = windowToInstant(windowKey);
        List<LeaderboardEntry> result =
                (since == null) ? eventRepository.totalsAll() : eventRepository.totalsSince(since);
        List<LeaderboardEntry> leaderboardEntryList = result.stream()
                .map(res -> new LeaderboardEntry(res.getCategory(), res.getPoints() == null ? 0L : res.getPoints()))
                .sorted((a, b) -> Long.compare(b.getPoints(), a.getPoints()))
                .toList();
        return leaderboardEntryList;
    }

    private Instant windowToInstant(String windowKey) {
        if (windowKey == null || windowKey.isEmpty() || "all".equals(windowKey)) {
            return null;
        }
        switch (windowKey) {
            case "5m":
                return Instant.now().minus(5, ChronoUnit.MINUTES);
            case "1h":
                return Instant.now().minus(1, ChronoUnit.HOURS);
            default:
                return null;
        }
    }

    public SseEmitter createEmitter(String windowKey) {
        SseEmitter emitter = new SseEmitter(0L);
        SSESubscriber subscriber = new SSESubscriber(emitter, windowKey);
        subscribers.add(subscriber);
        emitter.onCompletion(() -> subscribers.remove(subscriber));
        emitter.onTimeout(() -> subscribers.remove(subscriber));
        emitter.onError((ex) -> subscribers.remove(subscriber));
        try {
            List<LeaderboardEntry> getLeaderboardEntryList = getLeaderboardEntryList(windowKey);
            emitter.send(SseEmitter.event().name("init").data(getLeaderboardEntryList));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return emitter;
    }

}
