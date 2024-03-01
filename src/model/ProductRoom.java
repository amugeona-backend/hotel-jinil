package model;

import constant.RoomType;

import java.time.LocalDateTime;

public class ProductRoom extends Room {
    private final int roomNumber;
    private final LocalDateTime reservedDate;
    private boolean isReserved = false;

    public ProductRoom(RoomType roomType, int cost, int roomNumber, LocalDateTime reservedDate) {
        super(roomType, cost);
        this.roomNumber = roomNumber;
        this.reservedDate = reservedDate;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public LocalDateTime getReservedDate() {
        return reservedDate;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
         isReserved = reserved;
    }
}
