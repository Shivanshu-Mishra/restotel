package com.shiva.restotel.rest;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class RoomBookingModelAssembler implements RepresentationModelAssembler<RoomBookingV1,EntityModel<RoomBookingV1>> {

    @Override
    public EntityModel<RoomBookingV1> toModel(RoomBookingV1 booking) {
        return EntityModel.of(booking,
               linkTo(methodOn(RoomController.class).getBookingDetails(booking.getId())).withSelfRel(),
                linkTo(methodOn(RoomController.class).getAllBookings()).withRel("bookings"));

    }
}
