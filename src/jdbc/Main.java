package jdbc;

import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void menu() {
        System.out.println("안녕하세요. 서초병원입니다.");
        System.out.println("당신의 건강, 우리가 함께합니다. 쉽고 빠른 병원 예약을 시작해보세요.");
        System.out.println("================= 메인화면 ==================");
        System.out.println("1.로그인");
        System.out.println("2.계정생성");
        System.out.print(">>> ");
        menu(sc.nextInt());
    }
    public static void menu(int num) {
        if (num == 1) {
            login();
        } else if (num == 2) {
            createAccount();
        } else {
            return;
        }
    }
    public static void login() {
        System.out.println("================= 로그인 ==================");
        System.out.println("환자면 1번 의사면 2번을 선택해주세요.");
        System.out.println("1.환자");
        System.out.println("2.의사");
        System.out.print(">>> ");
        login(sc.nextInt());
    }
    public static void login(int num) {
        if (num == 1) {
            System.out.println("================= 환자 로그인 ==================");
            System.out.print("이름을 입력하세요 : ");
        }
        else if (num == 2) {
            System.out.println("================= 의사 로그인 ==================");
            System.out.print("이름을 입력하세요 : ");
        } else {
            return;
        }
    }
    public static void createAccount() {
        System.out.println("================= 계정 생성==================");
        System.out.println("소속을 선택해주세요");
        System.out.println("1.환자");
        System.out.println("2.의사");
        System.out.print(">>> ");
    }
    public static void createAccount(int num) {
        if (num == 1) {
            System.out.println("================= 환자 계정 생성 ==================");
            System.out.print("이름을 입력하세요 : ");
        }
        else if (num == 2) {
            System.out.println("================= 의사 계정 생성 ==================");
            System.out.print("이름을 입력하세요 : ");
        } else {
            return;
        }
    }
    public static void main(String[] args) {
        DBConnectionManager connectionManager = new DBConnectionManager();
        menu();
    }
}
