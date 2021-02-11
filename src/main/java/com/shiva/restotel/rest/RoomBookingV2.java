package com.shiva.restotel.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@ApiModel(description = "New booking format")
public class RoomBookingV2 {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Size(min = 2)
    @ApiModelProperty(value = "Customer name is mandatory for booking and name should container atleast 2 character")
    private String customerName;
    @NotNull
    private String identityProof;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Room room;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomBookingV2 that = (RoomBookingV2) o;
        return id.equals(that.id) &&
                customerName.equals(that.customerName) &&
                identityProof.equals(that.identityProof) &&
                room.equals(that.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerName, identityProof, room);
    }

    public RoomBookingV2() {
    }

    public RoomBookingV2(@NotNull @Size(min = 2) String customerName, @NotNull String identityProof, Room room) {
        this.customerName = customerName;
        this.identityProof = identityProof;
        this.room = room;
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

    public String getIdentityProof() {
        return identityProof;
    }

    public void setIdentityProof(String identityProof) {
        this.identityProof = identityProof;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "RoomBookingV2{" +
                "id='" + id + '\'' +
                ", customerName='" + customerName + '\'' +
                ", identityProof='" + identityProof + '}';
    }
}
