package service;

import repository.PatientRepository;

import java.util.List;
import java.util.Map;

import static ui.AppUi.*;
public class PatientService implements AppService {

    PatientRepository patientRepository = new PatientRepository();

    //AppService를 구현했기에 강제로 생성하는 메서드
    public void start(){
        patientMenuScreen();
    }

    public boolean isLogin(){
        boolean flag = false;

        System.out.println("이름을 입력해주세요.");
        String name = inputString(">>>");
        List<Map<String, Object>> userList = patientRepository.seachUser(name);
        if(userList.size() == 0 ){
            System.out.println("해당하는 회원이 없습니다.");
            return flag;
        };
        for (Map<String, Object> map : userList) {
            System.out.printf("%d. 환자이름: %s, 생년월일: %s, 전번뒷자리: %s \n",
                    map.get("userId"),map.get("userName"),map.get("userBirth"), map.get("backNumber"));
        }
        System.out.println("해당하는 회원번호를 입력해주세요.");
        int idx = inputInteger(">>>");
        int cnt = 0;
        for (Map<String, Object> map : userList) {
            if((Integer)map.get("userId") == idx){
                cnt++;
            }
        }
        if(cnt != 1){
            System.out.println("올바른 회원 번호를 선택해주세요.");
            isLogin();
        }

        System.out.println("비밀번호를 입력해주세요.");
        String password = inputString(">>>");
        int n = patientRepository.isUser(idx , password);
        if(n == 1){
            flag = true;
        }

        return flag;
    }
}
