package service;

import model.ProductRoom;
import model.Reservation;

import java.time.LocalDate;
import java.util.Scanner;

public class ManagerMode {
    private final HotelService hotelService;

    public ManagerMode(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    Scanner input = new Scanner(System.in);
    public void displayManagerMode() {
        System.out.println("--관리자 모드--");
        displayManagerMenu();
    }

    public void displayManagerMenu() {
        System.out.println();
        System.out.println("1. 예약 현황");
        System.out.println("2. 자산 현황");
        System.out.println("3. 로그아웃");
        System.out.print("입력 : ");
        int select = input.nextInt();
        switch (select) {
            case 1 -> reservationStatus();
            case 2 -> hotelMoneyStatus();
            case 3 -> logOut();
            default -> {
                System.out.println("1~3번 메뉴를 선택하세요.");
                displayManagerMenu();
            }
        }
    }

    public void reservationStatus() {
        System.out.println();
        System.out.println("메뉴를 선택해 주세요.");
        System.out.println("1. 빈객실 찾기");
        System.out.println("2. 예약 찾기");
        System.out.println("3. 오늘 예약 현황");
        System.out.println("4. 이전으로 가기");
        int select = input.nextInt();
        switch (select) {
            case 1 -> findEmptyRoom();
            case 2 -> findReservation();
            case 3 -> findReservationToday();
            case 4 -> displayManagerMenu();
            default -> {
                System.out.println("1~4번 메뉴를 선택하세요.");
                reservationStatus();
            }
        }
    }

    public void findEmptyRoom() {
        showHotelDate();
        int select = input.nextInt();
        if (select == 0) {
            reservationStatus();
        }
        else if (select > 0 && select <= hotelService.findAvailableDays().size()) {
            showEmptyRoomByDate(hotelService.findAvailableDays().get(select - 1));
        }
        else {
            System.out.println("잘못 입력하였습니다.");
            findEmptyRoom();
        }
    }

    public void showEmptyRoomByDate(LocalDate day) {
        System.out.println();
        System.out.println("선택하신 날짜는 " + day + "입니다.");
        for (ProductRoom productRoom : hotelService.findEmptyRoomByDate(day)) {
            System.out.println(productRoom.getRoomNumber() + "\t" + productRoom.getRoomType());
        }
        System.out.println("0. 이전으로 가기");
        int select = input.nextInt();
        if (select == 0) {
            reservationStatus();
        }
        else {
            System.out.println();
            showEmptyRoomByDate(day);
        }
    }

    public void findReservation() {
        System.out.println();
        System.out.println("예약 조회 방법을 선택하세요.");
        System.out.println("1. 이름으로 찾기");
        System.out.println("2. 전화번호로 찾기");
        System.out.println("3. 날짜로 찾기");
        System.out.println("4. 이전으로 가기");
        int select = input.nextInt();
        switch (select) {
            case 1 -> findReservationByExistingName();
            case 2 -> findReservationByCorrectPhoneNumber();
            case 3 -> findReservationByReservedDate();
            case 4 -> reservationStatus();
            default -> {
                System.out.println("1~4번 메뉴를 선택하세요.");
                findReservation();
            }
        }

    }

    public void findReservationByExistingName() {
        System.out.println();
        System.out.print("이름을 입력하세요 : ");
        String name = input.next();
        if (hotelService.findReservationByExistingName(name)) {
            showReservationByName(name);
        }
        else {
            System.out.println("예약 내역이 없습니다.");
            findReservation();
        }
    }

    public void showReservationByName(String name) {
        for (Reservation reservation : hotelService.findReservationByName(name)) {
            System.out.println(reservation.productRoom().getReservedDate() + "\t" + reservation.productRoom().getRoomType() + "\t"
            + reservation.userName() + "\t" + reservation.userPhoneNumber());
        }
        System.out.println("0. 이전으로 가기");
        int select = input.nextInt();
        if (select == 0) {
            findReservation();
        }
        else {
            System.out.println();
            showReservationByName(name);
        }
    }

    public void findReservationByCorrectPhoneNumber() {
        System.out.println();
        System.out.println("ex) 000-0000-0000");
        System.out.print("전화번호를 입력하세요 : ");
        String number = input.next();
        System.out.println(number);
        if (hotelService.correctPhoneNumber(number)) {
            if (hotelService.findReservationByExistingPhoneNumber(number)) {
                showReservationByPhoneNumber(number);
            }
            else {
                System.out.println("예약 내역이 없습니다.");
                findReservation();
            }
        }
        else {
            System.out.println("전화번호가 올바르지 않습니다.");
            findReservationByCorrectPhoneNumber();
        }
    }

    public void showReservationByPhoneNumber(String phoneNumber) {
        System.out.println();
        for (Reservation reservation : hotelService.findReservationByPhoneNumber(phoneNumber)) {
            System.out.println(reservation.productRoom().getReservedDate() + "\t" + reservation.productRoom().getRoomType() + "\t"
            + reservation.userName() + "\t" + reservation.userPhoneNumber());
        }
        System.out.println("0. 이전으로 가기");
        int select = input.nextInt();
        if (select == 0) {
            findReservation();
        }
        else {
            showReservationByPhoneNumber(phoneNumber);
        }
    }

    public void findReservationByReservedDate() {
        boolean reservationResult;
        showHotelDate();
        int select = input.nextInt();
        // 정수가 아닌 값이 들어왔을때 예외처리해줘야 함
        if (select == 0) {
            findReservation();
        }
        else if (select > 0 && select <= hotelService.findAvailableDays().size()) {
            reservationResult = findReservationByExistingDate(hotelService.findAvailableDays().get(select - 1));
            if (reservationResult) {
                reservationStatus();
            }
            else findReservationByReservedDate();
        }
        else {
            findReservationByReservedDate();
        }
    }

    public boolean findReservationByExistingDate(LocalDate date) {
        boolean bool;
        System.out.println();
        System.out.println("선택하신 날짜는 " + date + "입니다.");
        if (hotelService.findReservationByExistingDate(date)) {
            showReservationByDate(date);
            bool = true;
        }
        else {
            System.out.println("예약 내역이 없습니다.");
            bool = false;
        }
        return bool;
    }

    public void showReservationByDate(LocalDate date) {
        for (Reservation reservation : hotelService.findReservationByDate(date)) {
            System.out.println(reservation.productRoom().getReservedDate() + "\t" + reservation.productRoom().getRoomType() + "\t"
            + reservation.userName() + "\t" + reservation.userPhoneNumber());
        }
        System.out.println("0. 이전으로 가기");
        int select = input.nextInt();
        if (select == 0) {
            findReservation();
        }
        else {
            showReservationByDate(date);
        }
    }

    public void findReservationToday() {
        LocalDate localDate = LocalDate.now();
        findReservationByExistingDate(localDate);
        reservationStatus();
    }

    public void hotelMoneyStatus() {
        System.out.println("현재 호텔 자산은 " + hotelService.getHotelMoney() + "원 입니다.");
        displayManagerMenu();
    }

    public void logOut() {
        System.out.println("로그아웃 되었습니다.");
    }

    private void showHotelDate() {
        int i = 1;
        System.out.println();
        System.out.println("날짜를 선택하세요.");
        for (LocalDate day : hotelService.findAvailableDays()) {
            System.out.printf("%d. %10s", i++, day + "\n");
        }
        System.out.println("0. 이전으로 가기");
    }
}
