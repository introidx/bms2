package com.introidx.bms2.controller;

import com.introidx.bms2.entity.BMSEvents;
import com.introidx.bms2.service.DummyDataGenerator;
import com.introidx.bms2.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/events")
@RequiredArgsConstructor
public class EventsController {

    private final EventService eventService;

    @PostMapping("/create")
    public ResponseEntity<BMSEvents> createEvent(@RequestBody BMSEvents BMSEvents) {
        BMSEvents createdEvent = eventService.createEvent(BMSEvents);
        return ResponseEntity.ok(createdEvent);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<BMSEvents>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BMSEvents> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BMSEvents> updateEvent(@PathVariable Long id, @RequestBody BMSEvents updatedEvent) {
        return eventService.updateEvent(id, updatedEvent)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        boolean deleted = eventService.deleteEvent(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}