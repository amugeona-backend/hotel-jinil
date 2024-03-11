package service;

import model.ProductRoom;

public class ProductRoomService {
    public void changeReservationStatus (ProductRoom productRoom, boolean reservation) {
        productRoom.setReserved(reservation);
    }
}
