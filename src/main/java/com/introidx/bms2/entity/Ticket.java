package com.introidx.bms2.entity;

import com.introidx.bms2.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private Long id;
    private Long event_id;
    private Long user_id;
    private int qty;
    private List<Long> seats;
    private TicketStatus status;
    private Timestamp created_at;
    private boolean used;
}
