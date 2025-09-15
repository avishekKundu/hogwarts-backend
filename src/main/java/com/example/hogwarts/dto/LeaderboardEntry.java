package com.example.hogwarts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaderboardEntry {
    private String category;
    private Long points;

    public LeaderboardEntry() {}
    public LeaderboardEntry(String category, Long points) {
        this.category = category;
        this.points = points;
    }
}
