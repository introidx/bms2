package com.introidx.bms2.entity;

import com.introidx.bms2.enums.ArtistType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Area {
    private Long id;
    private String name;
    private List<Seats> seats;
}
