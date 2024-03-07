package model;

import constant.RoomType;

import java.time.LocalDate;

public class ProductRoom extends Room {
    private final int roomNumber;
    private final LocalDate reservedDate;
    private boolean isReserved = false;

    public ProductRoom(RoomType roomType, int cost, int roomNumber, LocalDate reservedDate) {
        super(roomType, cost);
        this.roomNumber = roomNumber;
        this.reservedDate = reservedDate;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public LocalDate getReservedDate() {
        return reservedDate;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
         isReserved = reserved;
    }
}
