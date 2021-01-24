package com.shiva.restotel.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RoomController {

    private final Register register;
    private final RoomBookingModelAssembler assembler;
    @Autowired
    private MessageSource messageSource;
    private Logger logger = LoggerFactory.getLogger(RoomController.class);

    public RoomController(Register register,RoomBookingModelAssembler assembler) {
        this.register = register;
        this.assembler=assembler;
    }

    @GetMapping("/")
    public String welcome(){
        return messageSource.getMessage("welcome.message",null, LocaleContextHolder.getLocale());
    }

    @GetMapping("/bookings")
    public CollectionModel<EntityModel<RoomBooking>> getAllBookings(){
        logger.debug("Get details of all bookings ");
        List<EntityModel<RoomBooking>> bookings=register.findAll().stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());
        return CollectionModel.of(bookings,linkTo(methodOn(RoomController.class).getAllBookings()).withSelfRel());
    }

    @PostMapping("/bookings")
    public ResponseEntity<?> booking(@RequestBody RoomBooking booking){
        logger.debug("Create new booking"+booking.toString());
        EntityModel<RoomBooking> model=assembler.toModel(register.save(booking));
      return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
              .body(model);
    }

    @GetMapping("/bookings/{id}")
    EntityModel<RoomBooking> getBookingDetails(@PathVariable Long id){
        logger.debug("Get details of booking id "+id);
      RoomBooking booking=register.findById(id).orElseThrow(() -> new BookingNotFound(id) );
      return assembler.toModel(booking);
    }

    @PutMapping("/bookings/{id}")
    ResponseEntity<?> updateBooking(@RequestBody RoomBooking newBooking,@PathVariable Long id){
        logger.debug("Booking Id to be updated"+id);
        logger.debug(newBooking.toString());
        RoomBooking roomBooking = register.findById(id)
                .map(booking ->{
                    booking.setCustomerName(newBooking.getCustomerName());
                    booking.setCheckIn(newBooking.getCheckIn());
                    booking.setRoomId(newBooking.getRoomId());
                    return register.save(booking);
                }).orElseGet(()->{
                    return register.save(newBooking);
                });

                EntityModel<RoomBooking> entityModel=EntityModel.of(roomBooking,
                linkTo(methodOn(RoomController.class).getBookingDetails(roomBooking.getId())).withSelfRel(),
                linkTo(methodOn(RoomController.class).getAllBookings()).withRel("bookings"));
                return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                        .body(entityModel);
    }

    @DeleteMapping("/bookings/{id}")
    ResponseEntity<?> cancelbooking(@PathVariable Long id){
        logger.debug("Booking Id to be updated"+id);
        register.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
