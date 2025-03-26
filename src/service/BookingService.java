package service;

import entity.Booking;
import entity.Department;
import entity.Patient;
import entity.UserDto;
import repository.BookingRepository;
import repository.DoctorRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static entity.Department.*;
import static ui.AppUi.*;

public class BookingService {

    private Scanner sc = new Scanner(System.in);
    BookingRepository bookingRepository = new BookingRepository();
    DoctorRepository doctorRepository = new DoctorRepository();

    private Booking booking;
    private UserDto userDto;

    private Patient patient;

    // 진료 예약
    public void insertBooking(UserDto userDto) {
        System.out.println("\n=============== 진료 예약 ===============");

        String dateStr;
        LocalDate date;
        String department = "";
        try {
            dateStr = inputString("방문 희망 날짜를 입력해주세요. (예: 2025-01-01) : ");
            date = LocalDate.parse(dateStr);
        } catch (Exception e) {
            System.out.println("날짜 형식이 잘못되었습니다.");
            return;
        }

        System.out.println("예약하실 의사선생님을 선택해주세요.");

        // 현재 활성화 된 의사를 모두 조회하는 함수 필요.
        // 리스트에서 나온 의사들 중 번호를 선택하게 하기
        // 선택받은 값이 리스트에 있다면 그걸 변수로 할당(int doc_id)
        // 없다면 다시 선택하게하기
        List<Map<String, Object>> userList = doctorRepository.seachAllDoc();

        if(userList.isEmpty()){
            System.out.println("해당하는 의사가 없습니다.");
            return;
        }
        for (Map<String, Object> map : userList) {
            System.out.printf("%d. 의사선생님 성함: %s, 부서명: %s\n", map.get("DOC_ID"),map.get("DOC_NAME"), Department.valueOf((String) map.get("DEPARTMENT")).getName() );
        }
        System.out.println("\n해당하는 회원번호를 입력해주세요.");
        int idx = inputInteger(">>> ");
        int cnt = 0;
        int docId = 0;
        String docName = "";
        UserDto user = new UserDto();
        for (Map<String, Object> map : userList) {
            if((Integer)map.get("DOC_ID") == idx) {
                cnt++;
                docId = idx;
                docName = (String) map.get("DOC_NAME");
            }
        }
        if(cnt != 1){
            System.out.println("올바른 회원 번호를 선택해주세요.");
            return;
        } else {
            booking = new Booking(userDto.getUserId(), docId,"", date,"N");
        }
        System.out.println("\n[" + date + " / " + docName + "]로 예약하시겠습니까?(Y/N)");
        String confirm = inputString(">>> ");


        if (confirm.equals("Y")) {
            bookingRepository.addBooking(booking);
            System.out.println("\n예약이 완료되었습니다.");
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

    }

    public void checkBooking(UserDto userDto) {
        System.out.println("\n=============== 예약 조회 ===============");
        List<Booking> bookings = bookingRepository.findBookingByDoctorName(userDto.getName());
        if (bookings.isEmpty()) {
            System.out.println("예약된 환자가 없습니다.");
            return;
        }

        if (bookings.size() > 0) {
            System.out.println(userDto.getName() + "의사선생님의 예약 조회 입니다.");
            for (Booking booking : bookings) {
                System.out.printf("%d. 환자이름: %s 생년월일: %s 예약날짜: %s",
                        booking.getBooking_id(), booking.getPatientName(), booking.getPatientBirth(), booking.getDate());
            }

            System.out.println("\n예약환자번호를 선택해주세요.");
            int chkBooking = inputInteger(">>> ");
            int cnt = 0;
            int bookingId = 0;
            for (Booking bk : bookings) {
                if (bk.getBooking_id() == chkBooking) {
                    cnt++;
                    bookingId = chkBooking;
                }
            }

            if (cnt != 1) {
                System.out.println("올바르지 않는 예약번호입니다.");
            } else {
                System.out.println("진료 내용 작성을 해주세요.");
                String content = inputString(">>> ");

                bookingRepository.insertContent(bookingId, content);

                System.out.println("작성이 완료되었습니다.");
            }

        }
    }

    // 예약 조회
    public List<Booking> searchBooking(UserDto userDto) {

        List<Booking> selectBooking = bookingRepository.getBookingByUser(userDto.getName());

        return selectBooking;

    }

    // 예약 취소
    public void deleteBooking(UserDto userDto) {
        System.out.println("\n=============== 예약 취소 ===============");
        List<Booking> bookingList = searchBooking(userDto);

        if (bookingList.isEmpty()) {
            System.out.println("취소할 예약이 없습니다.");
            return;
        }

        if (bookingList.size() > 0) {
            List<Integer> bookingNums = new ArrayList<>();
            System.out.println(userDto.getName() + "님의 예약 조회 입니다.");
            for (Booking booking : bookingList) {
                System.out.printf("%d. 이름: %s 생년월일: %s 예약날짜: %s\n",
                        booking.getBooking_id(), userDto.getName(), userDto.getBirth(), booking.getDate());
                bookingNums.add(booking.getBooking_id());
            }
            while (true){
                System.out.println("취소할 예약번호를 선택해주세요.");
                int delBooking = inputInteger(">>> ");
                if (bookingNums.contains(delBooking)) {
                    bookingRepository.deleteBooking(delBooking);
                    for (Booking booking : bookingList) {
                        if (booking.getBooking_id() == delBooking) {
                            System.out.println("정상적으로 취소되었습니다.");
                            break;
                        }
                    }
                    break;
                }else{
                    System.out.println("올바른 예약번호를 입력해 주세요!");
                }
            }

        }
    }


}
