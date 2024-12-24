package com.introidx.bms2.service;

import com.introidx.bms2.entity.BMSEvents;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventService {

    private List<BMSEvents> events = new ArrayList<>();

    public BMSEvents createEvent(BMSEvents event) {
        events.add(event);
        return event;
    }

    public List<BMSEvents> getAllEvents() {
        return events;
    }

    public Optional<BMSEvents> getEventById(Long id) {
        return events.stream()
                .filter(event -> event.getId().equals(id))
                .findFirst();
    }

    public Optional<BMSEvents> updateEvent(Long id, BMSEvents updatedEvent) {
        return getEventById(id).map(existingEvent -> {
            int index = events.indexOf(existingEvent);
            updatedEvent.setId(id);
            events.set(index, updatedEvent);
            return updatedEvent;
        });
    }

    public boolean deleteEvent(Long id) {
        return events.removeIf(event -> event.getId().equals(id));
    }
}
