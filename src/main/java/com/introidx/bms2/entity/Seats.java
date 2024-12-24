package com.introidx.bms2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seats {
    private Long id;
    private Long price;
    private String type;
    private boolean isBooked;
}
