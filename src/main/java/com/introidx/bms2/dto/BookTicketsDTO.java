package com.introidx.bms2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookTicketsDTO {
    private Long eventId;
    private Long userId;
    private Long qty;
    private Long areaId;
    private List<Long> seats;
}
