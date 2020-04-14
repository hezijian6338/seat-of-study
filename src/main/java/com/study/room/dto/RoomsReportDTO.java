package com.study.room.dto;

public class RoomsReportDTO {

    private int roomsCount;
    private int seatsCount;
    private int availableSeats;
    private int unAvailableSeats;

    public int getAvailableSeats() {
        return availableSeats;
    }

    public int getRoomsCount() {
        return roomsCount;
    }

    public int getSeatsCount() {
        return seatsCount;
    }

    public int getUnAvailableSeats() {
        return unAvailableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setRoomsCount(int roomsCount) {
        this.roomsCount = roomsCount;
    }

    public void setSeatsCount(int seatsCount) {
        this.seatsCount = seatsCount;
    }

    public void setUnAvailableSeats(int unAvailableSeats) {
        this.unAvailableSeats = unAvailableSeats;
    }
}
