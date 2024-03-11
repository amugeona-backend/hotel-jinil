package service;

import model.ProductRoom;
import model.Reservation;
import model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class UserMode {
    private final HotelService hotelService;
    private final UserService userService = new UserService();
    private final ProductRoomService productRoomService = new ProductRoomService();

    public UserMode(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    Scanner input = new Scanner(System.in);

    public void displayUserMode(User user) {
        System.out.println();
        System.out.println("1. 호텔 예약하기");
        System.out.println("2. 예약 조회하기");
        System.out.println("3. 예약 취소하기");
        System.out.println("4. 포인트 충전하기");
        System.out.println("5. 포인트 조회하기");
        System.out.println("6. 포인트 환전하기");
        System.out.println("7. 로그아웃");
        serviceInput(user);
    }

    public void serviceInput(User user) {
        System.out.println();
        System.out.print("입력 : ");
        int select = input.nextInt();
        switch (select) {
            case 1 -> showAvailableDate(user);
            case 2 -> findReservation(user);
            case 3 -> showReservationStatus(user);
            case 4 -> chargePoint(user);
            case 5 -> showPoint(user);
            case 6 -> exchangePoint(user);
            case 7 -> logOut();
            default -> {
                System.out.println("1~7번 메뉴를 선택하세요.");
                displayUserMode(user);
            }
        }
    }

    public void showAvailableDate (User user) {
        List<LocalDate> availableDate = hotelService.findAvailableDays();
        System.out.println("예약 하실 날짜를 선택하세요.");
        for (int i = 0; i < availableDate.size(); i++) {
            System.out.printf("%2d. %10s\n", i + 1, availableDate.get(i));
        }
        System.out.println("0. 이전으로 가기");
        System.out.print("입력 : ");
        int select = input.nextInt();
        if (select == 0) displayUserMode(user);
        else if (select >= 1 && select <= availableDate.size()) {
            showAvailableRoom(user, availableDate, select);
        }
        else {
            System.out.println("잘못된 입력입니다.");
            System.out.println("다시 입력해주세요.");
            showAvailableDate(user);
        }
    }

    public void showAvailableRoom (User user, List<LocalDate> availableDate, int select) {
        LocalDate date = availableDate.get(select - 1);
        List<ProductRoom> availabeRooms = hotelService.findEmptyRoomByDate(date);
        System.out.println("예약하실 객실을 선택하세요.");
        for (int i = 0; i < availabeRooms.size(); i++) {
            ProductRoom productRoom = availabeRooms.get(i);
            String isReserved = !productRoom.isReserved() ? "예약가능" : "예약불가능";
            System.out.printf("%2d. %4d호 | %-8s | %-6d ￦ | %-8s\n"
                    , i + 1, productRoom.getRoomNumber(), productRoom.getRoomType(), productRoom.getCost(), isReserved);
        }
        System.out.println("0. 이전으로 가기");
        System.out.print("입력 : ");
        int roomSelect = input.nextInt();
        if (roomSelect == 0) showAvailableDate(user);
        else if (roomSelect >= 1 && roomSelect <= availabeRooms.size()) {
            ProductRoom productRoom = availabeRooms.get(roomSelect - 1);
            if (productRoom.isReserved()) {
                System.out.println("이미 예약된 객실입니다.");
                showAvailableRoom(user, availableDate, select);
            } else {
                selectRoom(user, productRoom);
            }
        }
        else {
            System.out.println("잘못된 입력입니다.");
            System.out.println("다시 입력하세요.");
            showAvailableRoom(user, availableDate, select);
        }
    }

    public void selectRoom (User user, ProductRoom productRoom) {
        if (user.getUserMoney() >= productRoom.getCost()) {
            System.out.println();
            System.out.printf("%-4d호 | %-8s | %-6d ￦\n"
                    , productRoom.getRoomNumber(), productRoom.getRoomType(), productRoom.getCost());
            System.out.println("예약하시겠습니까?");
            System.out.println("1. 확인   2. 취소");
            System.out.print("입력 : ");
            int select = input.nextInt();
            if (select == 1) reserveRoom(user, productRoom);
            else if (select == 2) displayUserMode(user);
            else selectRoom(user, productRoom);
        }
        else {
            System.out.println(user.getName() + "님 포인트가 부족합니다.");
            System.out.println("1. 포인트 충전   2. 취소");
            System.out.print("입력 : ");
            int select = input.nextInt();
            if (select == 1) chargePoint(user);
            else if (select == 2) displayUserMode(user);
            else {
                System.out.println("잘못된 입력입니다.");
                displayUserMode(user);
            }
        }
    }

    public void reserveRoom (User user, ProductRoom productRoom) {
        Reservation reservation = new Reservation(productRoom, user.getName(), user.getPhoneNumber(), LocalDateTime.now());
        int roomCost = productRoom.getCost();
        hotelService.addReservation(reservation);
        userService.deductPoint(user, roomCost);
        productRoomService.changeReservationStatus(productRoom, true);
        System.out.println("예약이 완료되었습니다.");
        displayUserMode(user);
    }

    public void findReservation (User user) {
        String userPhoneNumber = user.getPhoneNumber();
        List<Reservation> reservations = hotelService.findReservationByPhoneNumber(userPhoneNumber);
        System.out.println();
        showReservation(reservations);
        System.out.println();
        System.out.println("메인화면으로 돌아갑니다.");
        displayUserMode(user);
    }

    public boolean showReservation (List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("조회 가능한 예약이 없습니다.");
            return false;
        }
        int i = 1;
        for (Reservation reservation : reservations) {
            ProductRoom productRoom = reservation.productRoom();
            System.out.printf("%2d. %10s | %3d호 | %8s | 예약한 시간 : %-15s\n"
            ,i++, productRoom.getReservedDate().toString(), productRoom.getRoomNumber(), productRoom.getRoomType(), reservation.reservedDate().toString());
        }
        return true;
    }

    public void showReservationStatus(User user) {
        String userPhoneNumber = user.getPhoneNumber();
        List<Reservation> reservations = hotelService.findReservationByPhoneNumber(userPhoneNumber);
        System.out.println();
        if (showReservation(reservations)) {
            System.out.println("취소할 예약을 선택하세요.");
            System.out.print("입력 : ");
            int select = input.nextInt();
            if (select >= 1 && select <= reservations.size()) {
                Reservation reservation = reservations.get(select - 1);
                cancelReservation(user, reservation);
            }
            else {
                System.out.println("잘못된 입력입니다.");
                showReservationStatus(user);
            }
        }
        else {
            displayUserMode(user);
        }
    }

    public void cancelReservation (User user, Reservation reservation) {
        ProductRoom productRoom = reservation.productRoom();
        System.out.printf("%10s | %3d호 | %8s | 예약한 시간 : %-15s\n"
        , productRoom.getReservedDate().toString(), productRoom.getRoomNumber(), productRoom.getRoomType(), reservation.reservedDate().toString());
        System.out.println();
        System.out.println("예약을 취소하시겠습니까?");
        System.out.println("1. 취소   2. 이전으로 가기");
        System.out.print("입력 : ");
        int select = input.nextInt();
        if (select == 1) {
            int roomCost = productRoom.getCost();
            hotelService.cancelReservation(reservation);
            userService.chargePoint(user, roomCost);
            productRoomService.changeReservationStatus(productRoom, false);
            System.out.println("예약이 취소되었습니다.");
        }
        displayUserMode(user);
    }

    public void chargePoint(User user) {
        System.out.println("충전할 포인트를 입력하세요.");
        int point = input.nextInt();
        userService.chargePoint(user, point);
        hotelService.addHotelMoney(point);
        System.out.println();
        System.out.println("충전이 완료되었습니다.");
        System.out.println(user.getName() + "님의 현재 잔액 : " + user.getUserMoney() + "원입니다.");
        displayUserMode(user);
    }

    public void showPoint(User user) {
        System.out.println();
        System.out.println(user.getName() + "님의 현재 잔액 : " + user.getUserMoney() + "원입니다.");
        System.out.println("1. 충전하기     2. 이전으로 가기");
        System.out.print("입력 : ");
        int select = input.nextInt();
        if (select == 1) {
            chargePoint(user);
        } else if (select == 2) {
            displayUserMode(user);
        }
        else {
            System.out.println("잘못된 입력입니다.");
            displayUserMode(user);
        }
    }

    public void exchangePoint(User user) {
        System.out.println(user.getName() + "님의 현재 잔액" + user.getUserMoney() + "원입니다.");
        System.out.println();
        System.out.print("환전할 금액을 입력하세요 : ");
        int point = input.nextInt();
        if (point > user.getUserMoney()) {
            System.out.println("환전 가능 최대 금액은 " + user.getUserMoney() + "원입니다.");
            exchangePoint(user);
        }
        else {
            System.out.println();
            System.out.println("환전이 완료되었습니다.");
            System.out.println(user.getName() + "님의 현재 잔액 : " + user.getUserMoney() + "원입니다.");
            userService.deductPoint(user, point);
            hotelService.deductHotelMoney(point);
            displayUserMode(user);
        }
    }

    public void logOut() {
        System.out.println("로그아웃되었습니다.");
    }

}
