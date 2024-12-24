package com.introidx.bms2.service;

import com.introidx.bms2.dto.BookTicketsDTO;
import com.introidx.bms2.dto.CheckAvailabilityDTO;
import com.introidx.bms2.entity.*;
import com.introidx.bms2.enums.PaymentOrderStatus;
import com.introidx.bms2.enums.TicketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final EventService eventService;
    private List<Ticket> tickets = new ArrayList<>();
    private HashMap<Integer, Boolean> paymentOrderMap = new HashMap<>();

    public Venue getSeats(Long eventId) {
        List<BMSEvents> list = eventService.getAllEvents();
        for (BMSEvents event : list) {
            if (event.getId().equals(eventId)) {
                return event.getVenue();
            }
        }
        return null;
    }

    public boolean checkAvailability(CheckAvailabilityDTO checkAvailabilityDTO) {
        List<BMSEvents> list = eventService.getAllEvents();
        for (BMSEvents event : list) {
            if (event.getId().equals(checkAvailabilityDTO.getEventId())) {
                List<Area> areas = event.getVenue().getAreas();
                for (Area area : areas) {
                    if (area.getId().equals(checkAvailabilityDTO.getAreaId())) {
                        List<Seats> seats = area.getSeats();
                        for (Seats seat : seats) {
                            if (checkAvailabilityDTO.getSeats().contains(seat.getId())) {
                                if (seat.isBooked()) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Ticket bookEvent(BookTicketsDTO bookTicketsDTO) {
        boolean available = checkAvailability(CheckAvailabilityDTO.builder()
                .eventId(bookTicketsDTO.getEventId())
                .areaId(bookTicketsDTO.getAreaId())
                .seats(bookTicketsDTO.getSeats())
                .build());

        if (available) {
            List<BMSEvents> list = eventService.getAllEvents();
            BMSEvents event = getEventById(bookTicketsDTO.getEventId(), list);
            Ticket ticket = null;
            if (event != null) {
                List<Area> areas = event.getVenue().getAreas();
                Area area = getAreaById(bookTicketsDTO.getAreaId(), areas);
                if (area != null) {
                    List<Seats> seats = area.getSeats();
                    List<Seats> selectedSeats = getSeatsByIds(bookTicketsDTO.getSeats(), seats);

                    for (Seats seat : selectedSeats) {
                        seat.setBooked(true);
                        putValueToCache(seat.getId().intValue());
                    }
                    area.setSeats(seats);
                    ticket = generateTickets(bookTicketsDTO, event, selectedSeats);
                }
                updateAreaInVenue(areas, area);
            }
            return ticket;
        }
        throw new RuntimeException("Seats are not available");
    }

    private Ticket generateTickets(BookTicketsDTO bookTicketsDTO, BMSEvents event, List<Seats> selectedSeats) {
        Ticket ticket = Ticket.builder()
                .id((long) (tickets.size() + 1))
                .event_id(event.getId())
                .user_id(bookTicketsDTO.getUserId())
                .qty(selectedSeats.size())
                .seats(getSeatIds(selectedSeats))
                .status(TicketStatus.PAYMENT_PENDING)
                .created_at(new java.sql.Timestamp(System.currentTimeMillis()))
                .used(false)
                .build();
        tickets.add(ticket);
        return ticket;
    }

    private List<Long> getSeatIds(List<Seats> selectedSeats) {
        List<Long> result = new ArrayList<>();
        for (Seats seat : selectedSeats) {
            result.add(seat.getId());
        }
        return result;
    }

    private BMSEvents getEventById(Long eventId, List<BMSEvents> list) {
        for (BMSEvents event : list) {
            if (event.getId().equals(eventId)) {
                return event;
            }
        }
        return null;
    }

    private void updateAreaInVenue(List<Area> areas, Area area) {
        for (Area a : areas) {
            if (a.getId().equals(area.getId())) {
                a = area;
            }
        }
    }

    private Area getAreaById(Long areaId, List<Area> areas) {
        for (Area area : areas) {
            if (area.getId().equals(areaId)) {
                return area;
            }
        }
        return null;
    }

    private List<Seats> getSeatsByIds(List<Long> seatIds, List<Seats> seats) {
        List<Seats> result = new ArrayList<>();
        for (Seats seat : seats) {
            if (seatIds.contains(seat.getId())) {
                result.add(seat);
            }
        }
        return result;
    }

    public Ticket getTicketById(Long ticketId) {
        for (Ticket ticket : tickets) {
            if (ticket.getId().equals(ticketId)) {
                return ticket;
            }
        }
        return null;
    }


    public void updateTicketStatus(Long ticketId, PaymentOrderStatus status) {
        Ticket ticket = getTicketById(ticketId);
        if (ticket != null) {
            if (status.equals(PaymentOrderStatus.SUCCESS)) {
                ticket.setStatus(TicketStatus.CONFIRMED);
                //  Remove key from cache
                updateCache(ticket);
            } else if (status.equals(PaymentOrderStatus.FAILED)) {
                ticket.setStatus(TicketStatus.FAILED);
                //  Remove key from cache
                updateCache(ticket);
                updateSeatNotBookedStatus(ticketId);
            }
            // update ticket list
            tickets.removeIf(t -> t.getId().equals(ticketId));
            tickets.add(ticket);
        }
    }

    private void updateSeatNotBookedStatus(Long ticketId) {
        Ticket ticket = getTicketById(ticketId);
        if (ticket == null) {
            throw new RuntimeException("Ticket not found");
        }

        BMSEvents event = getEventById(ticket.getEvent_id(), eventService.getAllEvents());
        if (event == null) {
            throw new RuntimeException("Event not found for ticket ID: " + ticketId);
        }

        List<Area> areas = event.getVenue().getAreas();
        boolean seatUpdated = false;

        for (Area area : areas) {
            List<Seats> seats = area.getSeats();
            for (Seats seat : seats) {
                if (ticket.getSeats().contains(seat.getId())) {
                    seat.setBooked(false);
                    seatUpdated = true;
                }
            }
        }

        if (seatUpdated) {
            eventService.updateEvent(event.getId(), event);
        } else {
            throw new RuntimeException("No matching seat found for ticket ID: " + ticketId);
        }
    }


    private void updateCache(Ticket ticket) {
        for (Long id : ticket.getSeats()) {
            removeFromCache(id.intValue());
        }
    }

    private void removeFromCache(int seatId) {
        // check if key is present in hm
        if (paymentOrderMap.containsKey(seatId)) {
            paymentOrderMap.remove(seatId);
        }
    }

    public void putValueToCache(int seatId) {
        if (paymentOrderMap.containsKey(seatId)) {
            throw new RuntimeException("Seat is already booked");
        }
        paymentOrderMap.put(seatId, true);
    }

    public List<Ticket> getAllTickets(Long eventId) {
        List<Ticket> result = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getEvent_id().equals(eventId)) {
                result.add(ticket);
            }
        }
        return result;
    }
}
