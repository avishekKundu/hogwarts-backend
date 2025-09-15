package com.example.hogwarts.repository;

import com.example.hogwarts.dto.LeaderboardEntry;
import com.example.hogwarts.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.Instant;
import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @Query("SELECT new com.example.hogwarts.dto.LeaderboardEntry(e.category, SUM(e.points)) " +
            "FROM EventEntity e WHERE e.timestamp >= :since GROUP BY e.category")
    List<LeaderboardEntry> totalsSince(Instant since);

    @Query("SELECT new com.example.hogwarts.dto.LeaderboardEntry(e.category, SUM(e.points)) " +
            "FROM EventEntity e GROUP BY e.category")
    List<LeaderboardEntry> totalsAll();

}
