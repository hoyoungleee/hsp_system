package main;

import jdbc.DBConnectionManager;


import java.util.Scanner;

import static ui.AppUi.*;

public class Main {

    public static void main(String[] args)
    {
        mainMenu();
    }

    public static void mainMenu(){

        MainController mc = new MainController();

        while(true){
            startScreen();
            int num = inputInteger(">>> ");
            if(num == -1){
                System.out.println("입력이 제대로 처리되지 못하였습니다.");
            }
            else if (num == 1) {
                mc.login();
            } else if (num == 2) {
                mc.createAccount();
            } else if (num==3){
                System.out.println("# 프로그램을 종료합니다.");
                break;
            }else {
                System.out.println("올바른 선택지를 입력하세요.");
            }
        }
    }
}
