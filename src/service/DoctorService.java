package service;

import entity.Department;
import entity.Doctor;
import entity.UserDto;
import repository.DoctorRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ui.AppUi.*;

public class DoctorService implements AppService {

    private final DoctorRepository doctorRepository = new DoctorRepository();

    @Override
    public void start(UserDto userDto){
        System.out.println(userDto.toString());
        while (true){
            doctorMenuScreen();
            int select = inputInteger(">>>");
            switch (select){
                case 1:{
                    System.out.println("진료예약신청이시작됩니다.");
                    break;
                }
                case 2:{
                    System.out.println("예약취소를 실행하는 자리");
                    break;
                }
                case 3:{
                    System.out.println("업무를 종료합니다.");
                    userDto = null;
                    break;
                }
                default:{
                    System.out.println("올바른 선택지를 입력해주세요.");
                }

            }
        }

    }

    public Map<String,Object> isLogin(){
        boolean flag = false;

        System.out.println("이름을 입력해주세요.");
        String name = inputString(">>>");
        //로그인 정보를 담기 위한 그릇

        Map<String, Object> info = new HashMap<>();


        List<Map<String, Object>> userList = doctorRepository.seachUser(name);
        if(userList.isEmpty()){
            System.out.println("해당하는 회원이 없습니다.");
            return info;
        }
        for (Map<String, Object> map : userList) {
            System.out.printf("%d. 의사선생님 성함: %s, 생년월일: %s, 전번뒷자리: %s \n", map.get("userId"),map.get("userName"),map.get("userBirth"), map.get("backNumber"));
        }
        System.out.println("해당하는 회원번호를 입력해주세요.");
        int idx = inputInteger(">>>");
        int cnt = 0;
        UserDto user = new UserDto();
        for (Map<String, Object> map : userList) {
            if((Integer)map.get("userId") == idx){
                cnt++;
                user.setUserId(idx);
            }
        }
        if(cnt != 1){
            System.out.println("올바른 회원 번호를 선택해주세요.");
            isLogin();
        }

        System.out.println("비밀번호를 입력해주세요.");
        String password = inputString(">>>");
        int n = doctorRepository.isUser(idx , password);
        if(n == 1){
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
        System.out.println("================= 부서 선택 ==================");
        System.out.println("부서를 선택해주세요.");
        System.out.println("1.정형외과 2.안과 3.내과 4.성형외과");
        int a = inputInteger(">> ");

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
            String name = inputString("회원명을 입력해주세요 :");
            String password = inputString("비밀번호를 입력해주세요 : ");
            Department department = selectDec();
            String phoneNumber;
            String birth;
            while (true){
                phoneNumber = inputString("전화번호를 입력해주세요(ex)000-0000-0000 : ");
                if (!isValidPhoneNumber(phoneNumber)) {
                    System.out.println("잘못된 입력입니다. 다시입력해주세요");
                }else {
                    break;
                }
            }
            while (true){
                birth = inputString("생년월일을 입력해주세요 (ex)YY/MM/DD : ");
                if (!isValidBirth(birth)) {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                }else{
                    break;
                }
            }



        Doctor doctor = new Doctor( name, password, phoneNumber, birth , "Y",department);

        doctorRepository.addDoctor(doctor);

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
                phoneNumber.startsWith("02")  || phoneNumber.startsWith("031") ||
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

        if(yearlength > 2){
            System.out.println("올바른 연도를 입력해주세요.");
            return false;
        }
        if(monthlength > 2){
            System.out.println("올바른 월을 입력해주세요.");
            return false;
        }
        if(!(month<=12 && month >0)){
            System.out.println("올바른 월을 입력해주세요.");
            return false;
        }
        if(daylength > 2){
            System.out.println("올바른 일자를 입력해주세요.");
            return false;
        }

        if(day >31){
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
}
