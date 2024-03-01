package service;

import model.User;
import java.util.Scanner;

public class userMode {
    Scanner input = new Scanner(System.in);
    public void displayUserMode() {
        System.out.println("환영합니다"); // 고객 이름 추가

    }

    public void displayUserMenu() {
        System.out.println();
        System.out.println("1. 호텔 예약하기");
        System.out.println("2. 예약 조회하기");
        System.out.println("3. 예약 취소하기");
        System.out.println("4. 포인트 충전하기");
        System.out.println("5. 포인트 조회하기");
        System.out.println("6. 포인트 환전하기");
        System.out.println("7. 로그아웃");
        serviceInput();
    }

    public void serviceInput() {
        System.out.println();
        System.out.print("입력 : ");
        int select = input.nextInt();
        switch (select) {

            default -> {
                System.out.println("1~7번 메뉴를 선택하세요.");
                displayUserMenu();
            }
        }
    }


}
