package com.introidx.bms2.dto;

import com.introidx.bms2.entity.Seats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckAvailabilityDTO {
    private Long eventId;
    private Long areaId;
    private List<Long> seats;
}
