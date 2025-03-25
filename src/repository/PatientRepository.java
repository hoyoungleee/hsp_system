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
    //해당 이름을 가진 유저 가져오기
    public List<Map<String, Object>> seachUser(String userName){
        //조건없이 결과물 불러오는 sql
//        String sql = "SELECT * FROM PATIENT_TB pt";
        //조건이 있는 결과물 불러오는 sql
        // String sql = "SELECT 가져올컬럼명 FROM sample WHERE 조건걸컬럼명 = ? ";
        //조건이 여러개 있는 결과물 불러오는 sql
        String sql = "SELECT * FROM PATIENT_TB pt WHERE pt.USER_NAME = ? AND ACTIVE = 'Y'";

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

    // 환자 계정 추가
    public void addPatient(Patient patient) {
        String sql = "INSERT INTO PATIENT_TB (user_id, password, user_name, phone_number, ACTIVE, user_birth) " +
                "VALUES(user_seq.NEXTVAL, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patient.getPassword());
            pstmt.setString(2, patient.getUser_name());
            pstmt.setString(3, patient.getPhone_number());
            pstmt.setString(4, patient.getActive());
            pstmt.setString(5, patient.getUser_brith());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
