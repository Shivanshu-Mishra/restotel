package com.shiva.restotel.rest;

public class BookingNotFound extends RuntimeException {

    public BookingNotFound(Long id){
        super("No booking found with id "+id);

    }
}
