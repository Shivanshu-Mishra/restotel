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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RoomController {

    private final RoomBookingRegisterV1 registerV1;
    private final RoomBookingRegisterV2 registerV2;
    private final RoomRegister roomRegister;
    private final RoomBookingModelAssembler assembler;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private PriceConfiguration priceConfiguration;
    private Logger logger = LoggerFactory.getLogger(RoomController.class);

    public RoomController(RoomBookingRegisterV1 registerV1, RoomBookingRegisterV2 registerV2, RoomRegister roomRegister, RoomBookingModelAssembler assembler) {
        this.registerV1 = registerV1;
        this.registerV2 = registerV2;
        this.roomRegister = roomRegister;
        this.assembler = assembler;
    }

    @GetMapping("/restotel")
    public String welcome() {
        return messageSource.getMessage("welcome.message", null, LocaleContextHolder.getLocale());
    }

    @GetMapping("/v1/bookings")
    public CollectionModel<EntityModel<RoomBookingV1>> getAllBookings() {
        logger.debug("Get details of all bookings ");
        List<EntityModel<RoomBookingV1>> bookings = registerV1.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(bookings, linkTo(methodOn(RoomController.class).getAllBookings()).withSelfRel());
    }

    @GetMapping("/v2/bookings")
    public List<RoomBookingV2> getAllBookingsV2() {
        return registerV2.findAll();
    }

    @GetMapping(value = "/bookings", params = "version=v1")
    public List<RoomBookingV1> getAllBookingV11() {
        return registerV1.findAll();
    }

    @GetMapping(value = "/bookings", params = "version=v2")
    public List<RoomBookingV2> getAllBookingV21() {
        return registerV2.findAll();
    }

    @GetMapping(value = "/bookings", headers = "X-API-VERSION=1")
    public List<RoomBookingV1> getAllBookingV12() {
        return registerV1.findAll();
    }

    @GetMapping(value = "/bookings", headers = "X-API-VERSION=2")
    public List<RoomBookingV2> getAllBookingV22() {
        return registerV2.findAll();
    }

    @GetMapping(value = "/bookings", produces = "application/v1+json")
    public List<RoomBookingV1> getAllBookingV13() {
        return registerV1.findAll();
    }

    @GetMapping(value = "/bookings", produces = "application/v2+json")
    public List<RoomBookingV2> getAllBookingV23() {
        return registerV2.findAll();
    }


    @PostMapping("/v1/bookings")
    public ResponseEntity<?> booking(@RequestBody RoomBookingV1 booking) {
        logger.debug("Create new booking" + booking.toString());
        EntityModel<RoomBookingV1> model = assembler.toModel(registerV1.save(booking));
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(model);
    }

    @GetMapping("/v1/bookings/{id}")
    EntityModel<RoomBookingV1> getBookingDetails(@PathVariable Long id) {
        logger.debug("Get details of booking id " + id);
        RoomBookingV1 booking = registerV1.findById(id).orElseThrow(() -> new BookingNotFound(id));
        return assembler.toModel(booking);
    }

    @PutMapping("/v1/bookings/{id}")
    ResponseEntity<?> updateBooking(@RequestBody RoomBookingV1 newBooking, @PathVariable Long id) {
        logger.debug("Booking Id to be updated" + id);
        logger.debug(newBooking.toString());
        RoomBookingV1 roomBooking = registerV1.findById(id)
                .map(booking -> {
                    booking.setCustomerName(newBooking.getCustomerName());
                    booking.setCheckIn(newBooking.getCheckIn());
                    booking.setRoomId(newBooking.getRoomId());
                    return registerV1.save(booking);
                }).orElseGet(() -> {
                    return registerV1.save(newBooking);
                });

        EntityModel<RoomBookingV1> entityModel = EntityModel.of(roomBooking,
                linkTo(methodOn(RoomController.class).getBookingDetails(roomBooking.getId())).withSelfRel(),
                linkTo(methodOn(RoomController.class).getAllBookings()).withRel("bookings"));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/bookings/{id}")
    ResponseEntity<?> cancelbooking(@PathVariable Long id) {
        logger.debug("Booking Id to be updated" + id);
        registerV1.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/bookings/rooms/{roomId}")
    public List<RoomBookingV2> getBookings(@PathVariable Long roomId) {
        return roomRegister.findById(roomId).orElseThrow(() -> new RoomNotFound("Room with "
                + roomId + " not found")).getRoomBookings();
    }

    @GetMapping("/bookings/rooms")
    public List<Room> getRooms() {
        return roomRegister.findAll();
    }

    @PostMapping("/v2/bookings")
    public ResponseEntity<RoomBookingV2> createBookings(@RequestBody RoomBookingV2 roomBookingV2) {
        logger.info("Create new booking" + roomBookingV2);
        Room room = roomBookingV2.getRoom();
        Optional<Room> optionalRoom = roomRegister.findById(room.getId());
        if (!optionalRoom.isPresent()) {
            roomRegister.save(room);
        }
        RoomBookingV2 newBooking = registerV2.save(roomBookingV2);
        URI loction = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newBooking).toUri();
        return ResponseEntity.created(loction).build();
    }

    @GetMapping("/bookings/prices")
    public Limit getRoomPrices() {
        return new Limit(priceConfiguration.getMinimum(),priceConfiguration.getMaximum());
    }
}
