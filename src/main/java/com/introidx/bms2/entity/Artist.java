package com.introidx.bms2.entity;


import com.introidx.bms2.enums.ArtistType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Artist {
    private Long id;
    private String name;
    private ArtistType artistType;
}
