package main;
import service.AppService;
import service.BookingService;
import service.DoctorService;
import service.PatientService;

import static ui.AppUi.*;
public class MainController {
    PatientService patientService = new PatientService();
    DoctorService doctorService = new DoctorService();


    public void login(){
        loginScreen();
        int num = inputInteger(">>>");
        System.out.println(num);
        if(num == 1){
           boolean flag =  patientService.isLogin();
           if(flag){
               patientService.start();
           }else {
               System.out.println("올바른 계정정보를 입력하세요.");
               login();
           }
        }else if (num ==2) {
            boolean flag =  doctorService.isLogin();
            if(flag){
                doctorService.start();
            }else {
                System.out.println("올바른 계정정보를 입력하세요.");
                login();
            }

        }
    }

    public void createAccount(){
        createAccountScreen();
        int num = inputInteger(">>>");
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
