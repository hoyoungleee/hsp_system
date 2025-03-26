package service;

import entity.Department;
import entity.Doctor;
import entity.UserDto;
import main.Main;
import repository.DoctorRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ui.AppUi.*;

public class DoctorService implements AppService {

    private final DoctorRepository doctorRepository = new DoctorRepository();
    BookingService bookingService = new BookingService();

    @Override
    public void start(UserDto userDto){
        while (true){
            doctorMenuScreen();
            int select = inputInteger(">>> ");
            switch (select){
                case 1:{
                    bookingService.checkBooking(userDto);
                    break;
                }
                case 2: {
                    updateDoctor(userDto);
                    break;
                }
                case 3: {
                    System.out.println("업무를 종료합니다.");
                    return;
                }
                default: {
                    System.out.println("올바른 선택지를 입력해주세요.");
                }

            }
        }

    }

    public Map<String, Object> isLogin() {
        boolean flag = false;

        System.out.println("이름을 입력해주세요.");
        String name = inputString(">>> ");
        //로그인 정보를 담기 위한 그릇

        Map<String, Object> info = new HashMap<>();


        List<Map<String, Object>> userList = doctorRepository.seachUser(name);
        if (userList.isEmpty()) {
            System.out.println("해당하는 회원이 없습니다.");
            info.put("flag", flag);
            return info;
        }
        for (Map<String, Object> map : userList) {
            System.out.printf("%d. 의사선생님 성함: %s, 생년월일: %s, 전번뒷자리: %s \n", map.get("userId"), map.get("userName"), map.get("userBirth"), map.get("backNumber"));
        }
        System.out.println("해당하는 회원번호를 입력해주세요.");
        int idx = inputInteger(">>> ");
        int cnt = 0;
        UserDto user = new UserDto();
        for (Map<String, Object> map : userList) {
            if ((Integer) map.get("userId") == idx) {
                cnt++;
                user.setUserId(idx);
            }
        }
        if (cnt != 1) {
            System.out.println("올바른 회원 번호를 선택해주세요.");
            info.put("flag", flag);
            return info;
        }

        System.out.println("비밀번호를 입력해주세요.");
        String password = inputString(">>> ");
        int n = doctorRepository.isUser(idx, password);
        if (n == 1) {
            flag = true;
            user.setLoginYn("Y");
            user.setName(name);
        }
        info.put("flag", flag);
        info.put("userInfo", user);
        return info;
    }

    // 부서 선택하는 메서드
    public static Department selectDec() {
        selectDepartmentScreen();
        int a = inputInteger(">>> ");

        if (a == 1) {
            return Department.ORTHOPEDICS;
        } else if (a == 2) {
            return Department.OPHTHALMOLOGY;
        } else if (a == 3) {
            return Department.INTERNAL_MEDICINE;
        } else if (a == 4) {
            return Department.PLASTIC_SURGERY;
        }
        return null;
    }


    // 의사 추가
    public void doctorJoin() {
        String name = inputString("회원명을 입력해주세요 : ");
        String password = inputString("비밀번호를 입력해주세요 : ");
        Department department = selectDec();
        String phoneNumber;
        String birth;
        while (true) {
            phoneNumber = inputString("전화번호를 입력해주세요(ex)000-0000-0000 : ");
            if (!isValidPhoneNumber(phoneNumber)) {
                System.out.println("잘못된 입력입니다. 다시입력해주세요");
            } else {
                break;
            }
        }
        while (true) {
            birth = inputString("생년월일을 입력해주세요 (ex)YY/MM/DD : ");
            if (!isValidBirth(birth)) {
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            } else {
                break;
            }
        }


        Doctor doctor = new Doctor(name, password, phoneNumber, birth, "Y", department);

        doctorRepository.addDoctor(doctor);
        System.out.println("회원가입이 완료 되었습니다.");

    }

    // 유효한 전화번호 확인
    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }

        for (char ch : phoneNumber.toCharArray()) {
            if (!Character.isDigit(ch) && ch != '-') {
                return false;
            }
        }

        if (phoneNumber.startsWith("-") || phoneNumber.endsWith("-")) {
            return false;
        }

        String onlyNumbers = phoneNumber.replaceAll("-", "");
        if (onlyNumbers.length() < 10 || onlyNumbers.length() > 11) {
            return false;
        }

        if (!(phoneNumber.startsWith("010") || phoneNumber.startsWith("011") ||
                phoneNumber.startsWith("016") || phoneNumber.startsWith("017") ||
                phoneNumber.startsWith("018") || phoneNumber.startsWith("019") ||
                phoneNumber.startsWith("02") || phoneNumber.startsWith("031") ||
                phoneNumber.startsWith("032") || phoneNumber.startsWith("041") ||
                phoneNumber.startsWith("051") || phoneNumber.startsWith("061"))) {
            return false;
        }

        String[] parts = phoneNumber.split("-");
        if (parts.length != 3) return false;
        if (parts[0].length() < 2 || parts[0].length() > 3) return false;
        if (parts[1].length() < 3 || parts[1].length() > 4) return false;
        if (parts[2].length() != 4) return false;

        return true;
    }


    // 생일 유효성 검증
    private boolean isValidBirth(String birth) {
        if (birth == null || birth.trim().isEmpty()) return false;

        String[] parts = birth.split("/");
        if (parts.length != 3) return false;

        for (String part : parts) {
            for (char ch : part.toCharArray()) {
                if (!Character.isDigit(ch)) return false;
            }
        }

        int year, month, day;
        try {
            year = Integer.parseInt(parts[0]);
            month = Integer.parseInt(parts[1]);
            day = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            return false;
        }
        //연도 자리수 구하기
        int yearlength = (int) (Math.log10(year) + 1);
        int monthlength = (int) (Math.log10(month) + 1);
        int daylength = (int) (Math.log10(day) + 1);

        if (yearlength > 2) {
            System.out.println("올바른 연도를 입력해주세요.");
            return false;
        }
        if (monthlength > 2) {
            System.out.println("올바른 월을 입력해주세요.");
            return false;
        }
        if (!(month <= 12 && month > 0)) {
            System.out.println("올바른 월을 입력해주세요.");
            return false;
        }
        if (daylength > 2) {
            System.out.println("올바른 일자를 입력해주세요.");
            return false;
        }

        if (day > 31) {
            System.out.println("올바른 일자를 입력해주세요.");
            return false;
        }

        int fullYear = (year >= 50) ? (1900 + year) : (2000 + year);
        try {
            LocalDate.of(fullYear, month, day);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }

    }

    public void updateDoctor(UserDto userDto){
        updateUserInformation();
        int num = inputInteger(">>> ");
        if (num == 1){
            modifyDoctor(userDto);
        } else if (num ==2) {
            System.out.println("정말 탈퇴하시겠습니까.(Y/N)");
            String str = inputString(">>> ");
            if(str.equals("Y")){
                delDoctor(userDto);
            }else{
                System.out.println("탈퇴를 취소합니다.");
            }
        } else if (num ==3) {
            System.out.println("수정화면을 나갑니다.");
        } else {
            System.out.println("올바른 선택지를 입력해주세요.");
            updateDoctor(userDto);
        }
    }

    public void modifyDoctor(UserDto userDto) {
        updateScreen();
        int num = inputInteger(">>> ");
        if (num == 1) {
            modifyPasswordDoctor(userDto);
        } else if (num == 2) {
            modifyPhoneNumber(userDto);
        }
    }

    public void modifyPasswordDoctor(UserDto userDto) {
        int id = userDto.getUserId();

        System.out.println("수정할 비밀번호를 입력하세요.");
        String newPassword = inputString("새 비밀번호: ");

        doctorRepository.updatePasswordDoctor(id, newPassword);

    }

    public void modifyPhoneNumber(UserDto userDto) {
        int id = userDto.getUserId();

        System.out.println("수정하실 전화번호를 입력하세요.");
        String newPhoneNumber = inputString("새 전화번호: ");
        if (!isValidPhoneNumber(newPhoneNumber)) {
            System.out.println("유효하지 않은 전화번호 입니다.");
        } else {
            doctorRepository.updateNumberDoctor(id, newPhoneNumber);
        }
    }
    public void delDoctor(UserDto userDto) {
        int delUserNum = userDto.getUserId();
        doctorRepository.delDoctor(delUserNum);
        System.out.println("회원 정보가 삭제되었습니다.");
        System.exit(0);
    }
}
