package com.introidx.bms2.service;

import com.introidx.bms2.entity.*;
import com.introidx.bms2.enums.ArtistType;
import com.introidx.bms2.enums.EventType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DummyDataGenerator {

    public static List<BMSEvents> generateDummyEvents() {
        Seats seat1 = Seats.builder()
                .id(1L)
                .price(150L)
                .type("Regular")
                .isBooked(false)
                .build();

        Seats seat2 = Seats.builder()
                .id(2L)
                .price(200L)
                .type("Premium")
                .isBooked(true)
                .build();

        Area area1 = Area.builder()
                .id(1L)
                .name("Main Hall")
                .seats(Arrays.asList(seat1, seat2))
                .build();

        Venue venue = Venue.builder()
                .id(1L)
                .areas(Arrays.asList(area1))
                .build();

        Artist artist1 = Artist.builder()
                .id(1L)
                .name("John Doe")
                .artistType(ArtistType.SOLO)
                .build();

        Artist artist2 = Artist.builder()
                .id(2L)
                .name("Jane Smith")
                .artistType(ArtistType.DANCER)
                .build();

        BMSEvents event1 = BMSEvents.builder()
                .id(1L)
                .name("Live Concert")
                .location("Downtown Arena")
                .city("New York")
                .state("NY")
                .timeDuration("3 hours")
                .startTime("7:00 PM")
                .eventType(EventType.CONCERT)
                .artists(Arrays.asList(artist1, artist2))
                .venue(venue)
                .bookingAllowedPerUser(4)
                .build();

        BMSEvents event2 = BMSEvents.builder()
                .id(2L)
                .name("Theater Play")
                .location("City Hall Theater")
                .city("Los Angeles")
                .state("CA")
                .timeDuration("2 hours")
                .startTime("6:00 PM")
                .eventType(EventType.PLAY)
                .artists(Arrays.asList(artist1))
                .venue(venue)
                .bookingAllowedPerUser(2)
                .build();

        return Arrays.asList(event1, event2);
    }
}

