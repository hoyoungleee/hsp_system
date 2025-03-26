package ui;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AppUi {

    static Scanner sc = new Scanner(System.in);

    public static String inputString(String message) {
        System.out.print(message);
        String str = sc.nextLine();
        return str;
    }

    public static int inputInteger(String message) {
        System.out.print(message);
        int num = 0;
        try {
            num = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("# 올바른 정수 입력값이 아닙니다!");
        } finally {
            sc.nextLine();
        }
        return num;
    }

    public static void makeLine() {
        System.out.println("========================================");
    }

    // 메인화면
    public static void startScreen() {
        System.out.println("\n안녕하세요. 서초병원입니다.");
        System.out.println("당신의 건강, 우리가 함께합니다. 쉽고 빠른 병원 예약을 시작해보세요.");
        System.out.println("=============== 메인화면 ================");
        System.out.println("1. 로그인");
        System.out.println("2. 계정생성");
        System.out.println("3. 프로그램 종료");
        makeLine();
    }

    // 로그인화면
    public static void loginScreen() {
        System.out.println("\n================ 로그인 =================");
        System.out.println("환자면 1번, 의사면 2번을 선택해주세요.");
        System.out.println("1. 환자");
        System.out.println("2. 의사");
        System.out.println("3. 첫 화면으로 가기");
        makeLine();
    }

    // 환자메뉴화면
    public static void patientMenuScreen() {
        System.out.println("\n=============== 환자 메뉴 ===============");
        System.out.println("1. 진료 예약 신청");
        System.out.println("2. 예약 취소");
        System.out.println("3. 진료비 내역");
        System.out.println("4. 회원 정보 수정");
        System.out.println("5. 종료");
        makeLine();
    }

    // 의사메뉴화면
    public static void doctorMenuScreen() {
        System.out.println("\n=============== 의사 메뉴 ===============");
        System.out.println("1. 예약 조회");
        System.out.println("2. 회원 정보 수정");
        System.out.println("3. 종료");
        makeLine();
    }

    // 회원정보수정화면
    public static void updateUserInformation() {
        System.out.println("\n============= 회원 정보 수정 =============");
        System.out.println("1. 수정하기");
        System.out.println("2. 탈퇴하기");
        System.out.println("3. 나가기");
        makeLine();
    }

    // 수정하기화면
    public static void updateScreen() {
        System.out.println("\n=============== 수정하기 ================");
        System.out.println("1. 비밀번호 변경");
        System.out.println("2. 전화번호 변경");
        System.out.println("3. 취소하기 (메인화면이동)");
        makeLine();
    }

    // 계정생성화면
    public static void createAccountScreen() {
        System.out.println("\n=============== 계정 생성 ===============");
        System.out.println("소속을 선택해주세요.");
        System.out.println("1. 환자");
        System.out.println("2. 의사");
        makeLine();
    }

    // 부서선택화면
    public static void selectDepartmentScreen() {
        System.out.println("\n=============== 부서 선택 ===============");
        System.out.println("부서를 선택해주세요.");
        System.out.println("1. 정형외과");
        System.out.println("2. 안과");
        System.out.println("3. 내과");
        System.out.println("4. 성형외과");
        makeLine();
    }

}
