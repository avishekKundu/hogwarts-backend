package com.example.hogwarts.dto;

import com.example.hogwarts.model.EventEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
public class Event {

    private Long id;

    @NotNull
    private String category;

    @NotNull
    private Integer points;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", shape = JsonFormat.Shape.STRING, timezone = "UTC")
    private Instant timestamp;

    public Event() {}

    public Event(Long id, String category, Integer points, Instant timestamp) {
        this.id = id;
        this.category = category;
        this.points = points;
        this.timestamp = timestamp;
    }

    public static Event fromEntity(EventEntity eventEntity) {
        return new Event(eventEntity.getId(),
                eventEntity.getCategory(),
                eventEntity.getPoints(),
                eventEntity.getTimestamp());
    }
}
