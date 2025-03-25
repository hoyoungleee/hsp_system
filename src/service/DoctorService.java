package service;

import repository.DoctorRepository;
import static ui.AppUi.*;

public class DoctorService {

    DoctorRepository doctorRepository = new DoctorRepository();

    public void modifyPasswordDoctor(UserDto userDto) {
        int id = userDto.getUserDto();

        System.out.println("수정할 비밀번호를 입력하세요.");
        String newPassword = inputString("새 비밀번호: ");

        doctorRepository.updatePasswordDoctor(id, newPassword);

    }

    public void modifyPhoneNumber(UserDto userDto) {
        int id = userDto.getUserDto();

        System.out.println("수정하실 전화번호를 입력하세요.");
        String newPhoneNumber = inputString("새 전화번호: ");

        doctorRepository.updateNumberDoctor(id,newPhoneNumber);
    }
}
