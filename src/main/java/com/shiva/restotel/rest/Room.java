package com.shiva.restotel.rest;

import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Room {
    @Id
    private Long id;
    @OneToMany(mappedBy = "room")
    private List<RoomBookingV2> roomBookings;
    private RoomType roomType;

    public Room() {
        roomBookings=new ArrayList<>();
    }

    public Room(Long id,RoomType roomType) {
        this.id = id;
        this.roomType = roomType;
        this.roomBookings=new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RoomBookingV2> getRoomBookings() {
        return roomBookings;
    }

    public void setRoomBooking(List<RoomBookingV2> roomBookings) {
        this.roomBookings = roomBookings;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
}
