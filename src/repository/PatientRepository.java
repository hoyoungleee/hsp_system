package repository;

import entity.Patient;
import jdbc.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientRepository {

    // 환자 계정 추가
    public void addPatient (Patient patient){

        String sql = "INSERT INTO PATIENT_TB (user_id, password, user_name, phone_number, del_yn, user_birth) " +
                "VALUES(user_seq.NEXTVAL, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patient.getPassword());
            pstmt.setString(2, patient.getUser_name());
            pstmt.setString(3, patient.getPhone_number());
            pstmt.setString(4, patient.getDel_yn());
            pstmt.setString(5, patient.getUser_brith());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    //해당 이름을 가진 유저 가져오기
    public List<Map<String, Object>> seachUser(String userName){
        //조건없이 결과물 불러오는 sql
//        String sql = "SELECT * FROM PATIENT_TB pt";
        //조건이 있는 결과물 불러오는 sql
        // String sql = "SELECT 가져올컬럼명 FROM sample WHERE 조건걸컬럼명 = ? ";
        //조건이 여러개 있는 결과물 불러오는 sql

        String sql = "SELECT * FROM PATIENT_TB pt WHERE pt.USER_NAME = ? AND del_yn = 'Y'";


        //목록데이터 담을 리스트변수
        List<Map<String, Object>> userList = new ArrayList<>();

        try(Connection conn = DBConnectionManager.getConnection();
            PreparedStatement prsmt = conn.prepareStatement(sql)){
            prsmt.setString(1, userName);

            //select할때는 excuteQuery() 로 실행 후 ResultSet으로 받아옵니다.
            ResultSet rs = prsmt.executeQuery();

            while (rs.next()){
                Map<String,Object> row = new HashMap<>();
                row.put("userId",rs.getInt("USER_ID"));
                row.put("userName",rs.getString("USER_NAME"));
                row.put("userBirth",rs.getString("USER_BIRTH"));
                String phoneNumber =   rs.getString("PHONE_NUMBER");
                String backNumber = phoneNumber.substring(phoneNumber.length() - 4);
                row.put("backNumber", backNumber);
                userList.add(row);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    //해당 번호와 비밀번호를 가진 유저 가져오기
    public int isUser(int userNum , String password){
        //조건없이 결과물 불러오는 sql
//        String sql = "SELECT * FROM PATIENT_TB pt";
        //조건이 있는 결과물 불러오는 sql
        // String sql = "SELECT 가져올컬럼명 FROM sample WHERE 조건걸컬럼명 = ? ";
        //조건이 여러개 있는 결과물 불러오는 sql
        String sql = "SELECT count(*) as total FROM PATIENT_TB pt WHERE pt.USER_ID = ? AND PASSWORD = ? ";

        //해당하는 유저를 담을 변수
        int n = 0;

        try(Connection conn = DBConnectionManager.getConnection();
            PreparedStatement prsmt = conn.prepareStatement(sql)){
            prsmt.setInt(1,userNum);
            prsmt.setString(2, password);
            //select할때는 excuteQuery() 로 실행 후 ResultSet으로 받아옵니다.
            ResultSet rs = prsmt.executeQuery();

            while (rs.next()){
                n = rs.getInt("total");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }


    public void updateNumberPatient(int user_id, String newPhoneNumber) {
        if (newPhoneNumber == null || newPhoneNumber.trim().isEmpty()) {
            System.out.println("새 전화번호는 반드시 입력해야 합니다.");
            return;
        }
        try (Connection conn = DBConnectionManager.getConnection()) {
            String sql = "SELECT phone_number FROM PATIENT_TB WHERE user_id =?";
            try (PreparedStatement checkpstmt = conn.prepareStatement(sql)) {
                checkpstmt.setInt(1, user_id);
                try (ResultSet rs = checkpstmt.executeQuery()) {
                    if (rs.next()) {
                        String updatedPhoneNumber = rs.getString("phone_number");
                        if (updatedPhoneNumber.equals(newPhoneNumber)) {
                            System.out.println("기존 전화번호와 동일합니다.");
                            return;
                        }
                    }
                }
            }

            sql = "UPDATE PATIENT_TB SET phone_number = ? WHERE user_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newPhoneNumber);
                pstmt.setInt(2, user_id);

                int i = pstmt.executeUpdate();
                System.out.println(i > 0 ? "전화번호 수정완료" : "수정실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePasswordPatient(int user_id, String newPassword) {
        if (newPassword == null || newPassword.trim().isEmpty()) {
            System.out.println("새 전화번호는 반드시 입력해야 합니다.");
            return;
        }
        try (Connection conn = DBConnectionManager.getConnection()) {
            String sql = "SELECT password FROM PATIENT_TB WHERE user_id = ?";
            try (PreparedStatement checkpstmt = conn.prepareStatement(sql)) {
                checkpstmt.setInt(1, user_id);
                try (ResultSet rs = checkpstmt.executeQuery()) {
                    if (rs.next()) {
                        String currPassword = rs.getString("password");
                        if (currPassword.equals(newPassword)) {
                            System.out.println("동일한 비밀번호입니다.");
                            return;
                        }
                    }
                }
            }

            sql = "UPDATE PATIENT_TB SET password = ? WHERE user_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newPassword);
                pstmt.setInt(2, user_id);


                int i = pstmt.executeUpdate();
                System.out.println(i > 0 ? "비밀번호 수정완료" : "수정 실패");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void delPatient(int delUserNum) {
        String sql = "UPDATE PATIENT_TB SET del_yn = 'N' WHERE USER_ID = ?";
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, delUserNum);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
