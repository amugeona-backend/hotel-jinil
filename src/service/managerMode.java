package service;

import java.util.Scanner;

public class managerMode {
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

    }

    public void hotelMoneyStatus() {

    }

    public void logOut() {
        System.out.println("로그아웃 되었습니다.");
    }
}
