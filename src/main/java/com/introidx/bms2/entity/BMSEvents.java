package com.introidx.bms2.entity;

import com.introidx.bms2.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BMSEvents {
    private Long id;
    private String name;
    private String location;
    private String city;
    private String state;
    private String timeDuration;
    private String startTime;
    private EventType eventType;
    private List<Artist> artists;
    private Venue venue;
    private Integer bookingAllowedPerUser;
}
