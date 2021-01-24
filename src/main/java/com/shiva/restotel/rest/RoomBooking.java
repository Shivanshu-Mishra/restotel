package com.shiva.restotel.rest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class RoomBooking {
    private @Id @GeneratedValue Long id;
    @NotNull @Size(min=2) private String customerName;
    @NotNull  private String checkIn;
    private String checkOut;
    @NotNull private Long roomId;

    public RoomBooking(){}

    @Override
    public String toString() {
        return "RoomBooking{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", checkIn='" + checkIn + '\'' +
                ", checkOut='" + checkOut + '\'' +
                ", roomId=" + roomId +
                '}';
    }

    public RoomBooking(String customerName, String checkIn, Long roomId) {
        this.customerName = customerName;
        this.checkIn = checkIn;
        this.roomId = roomId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomBooking that = (RoomBooking) o;
        return id.equals(that.id) && customerName.equals(that.customerName)  && roomId.equals(that.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerName, roomId);
    }
   }
