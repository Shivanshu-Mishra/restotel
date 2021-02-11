package com.shiva.restotel.rest;

public class RoomNotFound extends RuntimeException {

    public RoomNotFound(String message){
        super(message);
    }
}
