package main;
import service.AppService;
import service.BookingService;
import service.DoctorService;
import service.PatientService;

import static ui.AppUi.*;
public class MainController {
    PatientService patientService = new PatientService();
    BookingService bookingService = new BookingService();
    DoctorService doctorService = new DoctorService();


    public void login(){
        loginScreen();
        int num = inputInteger(">>>");
        System.out.println(num);
        if(num == 1){
           boolean flag =  patientService.isLogin();
           if(flag){
               patientService.start();
               int select = inputInteger(">>>");
           }else {
               System.out.println("올바른 계정정보를 입력하세요.");
               login();
           }
        }else if (num ==2) {
            doctorMenuScreen();
        }
    }

    public void createAccount(){

    }

}
