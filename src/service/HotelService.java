package service;

import constant.RoomType;
import model.Hotel;
import model.ProductRoom;
import model.Reservation;
import model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HotelService {
    Hotel hotel = new Hotel();
    private static HotelService hotelService;

    public static HotelService getHotelService() {
        if (hotelService == null) {
            hotelService = new HotelService();
        }
        return hotelService;
    }

    public void initRoom() {
        // 일주일간의 객실 추가
        LocalDate localDate = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            hotel.getProductRooms().add(new ProductRoom(RoomType.STANDARD, 100000, 101, localDate.plusDays(i)));
            hotel.getProductRooms().add(new ProductRoom(RoomType.SUPERIOR, 200000, 102, localDate.plusDays(i)));
            hotel.getProductRooms().add(new ProductRoom(RoomType.DELUXE, 300000, 103, localDate.plusDays(i)));
            hotel.getProductRooms().add(new ProductRoom(RoomType.SUITE, 400000, 104, localDate.plusDays(i)));
        }
    }

    public List<LocalDate> findAvailableDays() {
        return hotel.getProductRooms().stream()
                .map(ProductRoom::getReservedDate)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<ProductRoom> findEmptyRoomByDate(LocalDate day) {
        return hotel.getProductRooms().stream()
                .filter(room -> room.getReservedDate().equals(day))
                .filter(empty -> !empty.isReserved())
                .toList();
    }

    public boolean findReservationByExistingName(String name) {
        return hotel.getReservations().stream()
                .anyMatch(reservation -> reservation.userName().equals(name));
    }

    public List<Reservation> findReservationByName(String name) {
        return hotel.getReservations().stream()
                .filter(reservation -> reservation.userName().equals(name))
                .toList();
    }

    public boolean findReservationByExistingPhoneNumber(String phoneNumber) {
        return hotel.getReservations().stream()
                .anyMatch(reservation -> reservation.userPhoneNumber().equals(phoneNumber));
    }

    public List<Reservation> findReservationByPhoneNumber(String phoneNumber) {
        return hotel.getReservations().stream()
                .filter(reservation -> reservation.userPhoneNumber().equals(phoneNumber))
                .toList();
    }

    public boolean findReservationByExistingDate(LocalDate date) {
        return hotel.getReservations().stream()
                .anyMatch(reservation -> reservation.productRoom().getReservedDate().equals(date));
    }

    public List<Reservation> findReservationByDate(LocalDate date) {
        return hotel.getReservations().stream()
                .filter(reservation -> reservation.productRoom().getReservedDate().equals(date))
                .toList();
    }

    public int getHotelMoney() {
        return hotel.getHotel_money();
    }

    public void addUser (User user) {
        hotel.getUsers().add(user);
    }

    public boolean isUser (String id, String pw) {
        // 고객 아이디, 비밀번호 확인
        return ( hotel.getUsers().stream()
                .filter(user -> user.getUserId().equals(id))
                .anyMatch(user -> user.getUserPw().equals(pw)) );
    }

    public boolean isManager(String id, String pw) {
        // 관리자 아이디, 비밀번호 확인
        return ( id.equals(hotel.getId()) && pw.equals(hotel.getPassword()) );
    }

    public boolean correctPhoneNumber (String phoneNumber) {
        // 000-0000-0000 형태의 전화번호인지 확인
        String pattern = "^\\d{3}-\\d{4}-\\d{4}$";
        return Pattern.matches(pattern, phoneNumber);
    }

    public boolean existPhoneNumber (String phoneNumber) {
        // 전화번호 중복검사
        return hotel.getUsers().stream().noneMatch(u -> u.getPhoneNumber().equals(phoneNumber));
    }

    public boolean existId (String id) {
        // 아이디 중복검사
        // 반환값 변경
        return hotel.getUsers().stream().noneMatch(u -> u.getUserId().equals(id));
    }

    public User findUserById (String id) {
        return hotel.getUsers().stream()
                .filter(u -> u.getUserId().equals(id))
                .findFirst().orElse(null);  //.orElse(매개변수) : 값이 null일 경우 매개변수를 반환
    }

    public void addReservation(Reservation reservation) {
        hotel.getReservations().add(reservation);
    }

    public void cancelReservation(Reservation reservation) {
        hotel.getReservations().remove(reservation);
    }

    public void addHotelMoney(int money) {
        hotel.setHotel_money(hotel.getHotel_money() + money);
    }

    public void deductHotelMoney(int money) {
        hotel.setHotel_money(hotel.getHotel_money() - money);
    }

}
