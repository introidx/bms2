package com.introidx.bms2.enums;

public enum ArtistType {
    SOLO("SOLO"),
    BAND("BAND"),
    DANCER("DANCER"),;

    private final String value;

    ArtistType(String value) {
        this.value = value;
    }
}
