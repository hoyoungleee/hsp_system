package main;
import entity.UserDto;
import service.AppService;
import service.BookingService;
import service.DoctorService;
import service.PatientService;

import java.util.List;
import java.util.Map;

import static ui.AppUi.*;
public class MainController {
    PatientService patientService = new PatientService();
    DoctorService doctorService = new DoctorService();


    public void login(){
        loginScreen();
        int num = inputInteger(">>> ");
        if(num == 1){
           Map<String,Object> loginInfo =  patientService.isLogin();

           if((boolean)loginInfo.get("flag")){
               System.out.println("로그인에 성공하셨습니다.");
               patientService.start((UserDto)loginInfo.get("userInfo"));
           }else {
               System.out.println("올바른 계정정보를 입력하세요.");
               login();
           }
        }else if (num ==2) {
            Map<String,Object> loginInfo =  doctorService.isLogin();

            if((boolean)loginInfo.get("flag")){
                System.out.println("로그인에 성공하셨습니다.");
                doctorService.start((UserDto)loginInfo.get("userInfo"));
            }else {
                System.out.println("올바른 계정정보를 입력하세요.");
                login();
            }
        }
    }

    public void createAccount(){
        createAccountScreen();
        int num = inputInteger(">>> ");
        if(num == 1){
            patientService.patientJoin();
        }else if(num == 2){
            doctorService.doctorJoin();
        }
        else {
            System.out.println("올바른 사용자 유형을 선택해주세요.");
            createAccountScreen();
        }

    }

}
