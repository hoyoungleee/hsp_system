package service;

import entity.Booking;
import jdk.swing.interop.SwingInterOpUtils;
import repository.BookingRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static ui.AppUi.*;

public class BookingService {

    private Scanner sc = new Scanner(System.in);

    public void start() {

        while (true) {
            patientMenuScreen();
            int selection = inputInteger(">>> ");

            switch (selection) {
                case 1:
                    insertBooking();
                    break;
                case 2:
                    deleteBooking();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("# 메뉴를 다시 선택해주세요.");
            }
        }

    }

    // 진료 예약
    public void insertBooking() {
        System.out.println("\n=============== 진료 예약 ===============");
        System.out.print("방문 희망 날짜를 입력해주세요. : ");
        String date = sc.nextLine();

        System.out.println("\n방문할 부서를 입력해주세요.");
        System.out.println("1. 정형외과");
        System.out.println("2. 안과");
        System.out.println("3. 내과");
        System.out.println("4. 성형외과");
        int selection = inputInteger(">>> ");
        String department = "";
        switch (selection) {
            case 1:
                department = "정형외과";
                break;
            case 2:
                department = "안과";
                break;
            case 3:
                department = "내과";
                break;
            case 4:
                department = "성형외과";
                break;
            default:
                System.out.println("\n해당 부서가 존재하지 않습니다.");
        }
        System.out.println("\n[" + date + " / " + department + "]로 예약하시겠습니까?");
        String confirm = inputString(">>> ");

        if (confirm.equals("Y")) {
            System.out.println("\n예약이 완료되었습니다.");
            patientMenuScreen();
        } else if (confirm.equals("N")) {
            System.out.println("\n1. 이전으로 돌아가기");
            System.out.println("\n2. 다시 진료 예약하기");
            int choice = inputInteger(">>> ");

            if (choice == 1) {
                patientMenuScreen();
            } else if (choice == 2) {
                insertBooking();
            } else {
                System.out.println("\n잘못된 입력입니다.");
            }
        }
    }

    // 예약 취소
    public void deleteBooking() {
        System.out.println("\n=============== 예약 취소 ===============");
        List<Booking> bookingList = searchBooking();

        if (bookingList.isEmpty()) {
            System.out.println("취소할 예약이 없습니다.");
            return;
        }

        if (bookingList.size() > 0) {
            List<Integer> bookingNums = new ArrayList<>();
            System.out.println(Booking.getName + "님의 예약 조회 입니다.");
            for (Booking booking : bookingList) {
                System.out.printf("%d. 이름: %s 생년월일: %s 예약날짜: %s",
                        booking.getBooking_id(), booking.getUser_id(), booking.getDate())
                bookingNums.add(booking.getBooking_id());
            }
            System.out.println("\n취소할 예약번호를 선택해주세요.");
            int delBooking = inputInteger(">>> ");

            if (bookingNums.contains(delBooking)) {
                bookingRepository.deleteBooking(delBooking);
                for (Booking booking : bookingList) {
                    if (booking.getBooking_id() == delBooking) {
                        System.out.println("정말로 예약을 취소하시겠습니까? ");
                        break;
                    }
                }
                String deleteBooking = inputString(">>> ");
                if (deleteBooking.equals("Y")) {
                    System.out.println("\n예약이 정상적으로 취소되었습니다.");
                } else if (deleteBooking.equals("N")) {
                    System.out.println("\n예약 취소를 취소하였습니다.");
                } else {
                    System.out.println("\n잘못된 입력입니다.");
                }


            }
        }
    }


}
