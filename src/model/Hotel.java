package model;

import java.util.ArrayList;
import java.util.List;

import static constant.HotelConstant.HOTEL_MONEY;
import static constant.HotelConstant.PASSWORD;
import static constant.HotelConstant.ID;

public class Hotel {
    private final List<ProductRoom> productRooms = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private int hotel_money = HOTEL_MONEY;

    public List<ProductRoom> getProductRooms() {
        return productRooms;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public List<User> getUsers() {
        return users;
    }

    public int getHotel_money() { return hotel_money; }

    public String getId() { return ID;}

    public String getPassword() {
        return PASSWORD;
    }

    public void setHotel_money(int money) {
        this.hotel_money = money;
    }
}
