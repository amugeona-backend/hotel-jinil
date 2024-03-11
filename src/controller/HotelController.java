package controller;

import model.User;

import service.ManagerMode;
import service.UserMode;
import service.HotelService;

import java.util.Scanner;

import static java.lang.System.exit;

import static service.HotelService.getHotelService;
public class HotelController {

    HotelService hotelService = getHotelService();
    ManagerMode managerMode = new ManagerMode(hotelService);
    UserMode userMode = new UserMode(hotelService);

    Scanner input = new Scanner(System.in);
    public void run () {
        hotelService.initRoom();
        System.out.println("안녕하세요! JAVA호텔입니다.");
        startMenu();
    }

    public void startMenu () {
        while (true) {

            System.out.println("0. 프로그램 종료");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            // 계정 찾기 추가하기
            int select = input.nextInt();
            if (select == 0) {
                System.out.println("프로그램을 종료합니다.");
                exit(0);
            }
            else if (select == 1) signIn();
            else if (select == 2) signUp();
            else {
                System.out.println("1또는 2를 입력하세요.");
                startMenu();
            }

        }
    }

    public void signIn() {
        System.out.print("아이디 : ");
        String id = input.next();
        System.out.print("비밀번호 : ");
        String pw = input.next();
        // id, pw 유효한지 확인
        // 관리자 아이디일 경우 관리자모드로 로그인
        if (hotelService.isManager(id, pw)) {
            System.out.println();
            managerMode.displayManagerMode();
        }
        // 고객 아이디일 경우 고객모드로 로그인
        else if (hotelService.isUser(id, pw)) {
            User user = hotelService.findUserById(id);

            System.out.println();
            System.out.println("환영합니다. " + user.getName() + "님");
            userMode.displayUserMode(user);
        }
        else {
            System.out.println("아이디 혹은 비밀번호가 올바르지 않습니다.");
            startMenu();
        }
    }

    public void signUp() {
        boolean validPhoneNumber = false;
        boolean validId = false;
        boolean validPw = false;

        String name;
        String phoneNumber = null;
        String id = null;
        String pw = null;

        while (!validPhoneNumber) {
            System.out.println("ex) 000-0000-0000");
            System.out.print("전화번호 : ");
            phoneNumber = input.next();
            // 전화번호 유효검사
            // 전화번호 중복검사
            if (hotelService.correctPhoneNumber(phoneNumber)) {
                if (hotelService.existPhoneNumber(phoneNumber)) {
                    validPhoneNumber = true;
                    System.out.println("사용가능한 전화번호입니다.");
                    System.out.println();
                }
                else {
                    System.out.println("존재하는 전화번호입니다.");
                    System.out.println("다른 전화번호를 입력해주세요.");
                    System.out.println();
                }
            }
            else {
                System.out.println("유효하지 않은 전화번호입니다.");
                System.out.println("전화번호를 형식에 맞게 입력해주세요.");
                System.out.println();
            }
        }
        while (!validId) {
            System.out.print("아이디 : ");
            id = input.next();
            // 아이디 중복검사
            if (hotelService.existId(id)) {
                validId = true;
                System.out.println("사용가능한 id입니다.");
            }
            else {
                System.out.println("존재하는 id입니다.");
                System.out.println();
            }
        }
        while (!validPw) {
            System.out.print("비밀번호 : ");
            pw = input.next();
            System.out.print("비밀번호 확인 : ");
            String pw2 = input.next();
            if (pw.equals(pw2)) {
                validPw = true;
                System.out.println("비밀번호가 일치합니다.");
            }
            else {
                System.out.println("비밀번호가 일치하지 않습니다.");
                System.out.println("다시 입력해주세요.");
                System.out.println();
            }
        }
        System.out.print("이름 : ");
        name = input.next();
        // 유저 생성
        hotelService.addUser(new User(name, phoneNumber, id, pw));

        System.out.println("회원가입이 완료되었습니다!!");

        startMenu();
    }

}
