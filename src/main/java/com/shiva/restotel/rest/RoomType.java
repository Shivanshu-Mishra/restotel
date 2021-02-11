package com.shiva.restotel.rest;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum RoomType {
    AC("AC"),NONAC("NONAC");

    public String getType() {
        return type;
    }

    private final String type;
    RoomType(String type){
        this.type=type;
    }
}
