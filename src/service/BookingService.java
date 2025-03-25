package service;

import entity.Booking;
import entity.Patient;
import entity.UserDto;
import repository.BookingRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static entity.Department.*;
import static ui.AppUi.*;

public class BookingService {

    private Scanner sc = new Scanner(System.in);
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    private Booking booking;
    private UserDto userDto;
    public void setUserDto(UserDto userDto){
        this.userDto= userDto;
    }
    private Patient patient;

    // 진료 예약
    public void insertBooking(UserDto userDto) {
        System.out.println("\n=============== 진료 예약 ===============");
        String dateStr = inputString("방문 희망 날짜를 입력해주세요. : ");
        LocalDate date;

        try {
            date = LocalDate.parse(dateStr);
        } catch (Exception e) {
            System.out.println("날짜 형식이 잘못되었습니다.");
            return;
        }

        System.out.println("\n방문할 부서를 입력해주세요.");
        System.out.println("1. 정형외과");
        System.out.println("2. 안과");
        System.out.println("3. 내과");
        System.out.println("4. 성형외과");
        int selection = inputInteger(">>> ");
        String department = "";
        switch (selection) {
            case 1:
                department = String.valueOf(ORTHOPEDICS); // 정형외과
                break;
            case 2:
                department = String.valueOf(OPHTHALMOLOGY); // 안과
                break;
            case 3:
                department = String.valueOf(INTERNAL_MEDICINE); // 내과
                break;
            case 4:
                department = String.valueOf(PLASTIC_SURGERY); // 성형외과
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
                insertBooking(userDto);
            } else {
                System.out.println("\n잘못된 입력입니다.");
            }
        }

        bookingRepository.addBooking(booking);

    }

    // 예약 조회
    public List<Booking> searchBooking() {
        System.out.println("\n=============== 예약 조회 ===============");
        List<Booking> SearchUser =bookingRepository.getBookingByUser(userDto.getName());
        System.out.println(SearchUser);
        return SearchUser;
    }

    // 예약 취소
    public void deleteBooking(UserDto userDto) {
        System.out.println("\n=============== 예약 취소 ===============");
        List<Booking> bookingList = searchBooking();

        if (bookingList.isEmpty()) {
            System.out.println("취소할 예약이 없습니다.");
            return;
        }

        if (bookingList.size() > 0) {
            List<Integer> bookingNums = new ArrayList<>();
            System.out.println(userDto.getName() + "님의 예약 조회 입니다.");
            for (Booking booking : bookingList) {
                System.out.printf("%d. 이름: %s 생년월일: %s 예약날짜: %s",
                        booking.getBooking_id(), userDto.getName(), patient.getUser_brith(), booking.getDate());
                bookingNums.add(booking.getBooking_id());
            }

            System.out.println("\n취소할 예약번호를 선택해주세요.");
            int delBooking = inputInteger(">>> ");
            if (bookingNums.contains(delBooking)) {
                bookingRepository.deleteBooking(delBooking);
                for (Booking booking : bookingList) {
                    if (booking.getBooking_id() == delBooking) {
                        System.out.println("정상적으로 취소되었습니다.");
                        break;
                    }
                }
            }
            patientMenuScreen();
        }
    }


}
