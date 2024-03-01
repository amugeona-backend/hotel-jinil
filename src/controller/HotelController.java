package controller;

import model.Hotel;

import java.util.Scanner;
public class HotelController {
    Hotel hotel = new Hotel();
    Scanner input = new Scanner(System.in);
    public void run () {
        System.out.println("안녕하세요! JAVA호텔입니다.");
        startMenu();
    }

    public void startMenu () {
        System.out.println("1. 로그인");
        System.out.println("2. 회원가입");
        int select = input.nextInt();
        switch (select) {
            case 1 -> logIn();
            case 2 -> signIn();
            default -> {
                System.out.println("1또는 2를 입력하세요.");
                startMenu();
            }
        }
    }

    public void logIn() {
        System.out.print("아이디 : ");
        String id = input.next();
        System.out.print("비밀번호 : ");
        String pw = input.next();
        // id, pw 유효한지 확인
        // 관리자 아이디일 경우 관리자모드로 로그인
        // 고객 아이디일 경우 고객모드로 로그인
    }

    public void checkIdPw (String id, String pw) {
        // 아이디, 비밀번호 확인
        // 추후에 반환값 변경
    }

    public void isManager(String id) {
        // 관리자 아이디인지 확인
        // 추후에 반환값 변경
    }

    public void signIn() {
        System.out.print("이름 : ");
        String name = input.next();
        System.out.println("ex) 000-0000-0000");
        System.out.print("전화번호 : ");
        String phoneNumber = input.next();
        // 전화번호 유효검사
        // 전화번호 중복검사
        System.out.print("아이디 : ");
        String id = input.next();
        // 아이디 중복검사
        System.out.print("비밀번호 : ");
        String pw = input.next();
        // 유저 클래스에 정보 저장
    }

    public void correctPhoneNumber (String phoneNumber) {
        // 000-0000-0000 형태의 전화번호인지 확인
        // 반환값 변경
    }

    public void existPhoneNumber (String phoneNumber) {
        // 전화번호 중복검사
        // 반환값 변경
    }

    public void existId (String id) {
        // 아이디 중복검사
        // 반환값 변경
    }

}
