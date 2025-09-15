package com.example.hogwarts.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Entity
@Table(name = "events", indexes = {
        @Index(name = "idx_ts", columnList = "timestamp")
})
@Getter
@Setter
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Integer points;

    @Column(nullable = false)
    private Instant timestamp;

    public EventEntity() {}

    public EventEntity(Long id, String category, Integer points, Instant timestamp) {
        this.id = id;
        this.category = category;
        this.points = points;
        this.timestamp = timestamp;
    }
}
