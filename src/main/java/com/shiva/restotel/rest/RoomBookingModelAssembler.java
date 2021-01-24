package com.shiva.restotel.rest;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class RoomBookingModelAssembler implements RepresentationModelAssembler<RoomBooking,EntityModel<RoomBooking>> {

    @Override
    public EntityModel<RoomBooking> toModel(RoomBooking booking) {
        return EntityModel.of(booking,
               linkTo(methodOn(RoomController.class).getBookingDetails(booking.getId())).withSelfRel(),
                linkTo(methodOn(RoomController.class).getAllBookings()).withRel("bookings"));

    }
}
