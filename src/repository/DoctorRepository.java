package repository;

import entity.Doctor;

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

public class DoctorRepository {

    //해당 이름을 가진 유저 가져오기
    public List<Map<String, Object>> seachUser(String userName) {
        //조건없이 결과물 불러오는 sql
//        String sql = "SELECT * FROM PATIENT_TB pt";
        //조건이 있는 결과물 불러오는 sql
        // String sql = "SELECT 가져올컬럼명 FROM sample WHERE 조건걸컬럼명 = ? ";
        //조건이 여러개 있는 결과물 불러오는 sql

        String sql = "SELECT * FROM DOCTOR_TB WHERE DOC_NAME = ? AND del_yn = 'Y'";

        //목록데이터 담을 리스트변수
        List<Map<String, Object>> userList = new ArrayList<>();

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement prsmt = conn.prepareStatement(sql)) {
            prsmt.setString(1, userName);

            //select할때는 excuteQuery() 로 실행 후 ResultSet으로 받아옵니다.
            ResultSet rs = prsmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("userId", rs.getInt("DOC_ID"));
                row.put("userName", rs.getString("DOC_NAME"));
                row.put("userBirth", rs.getString("DOC_BIRTH"));
                String phoneNumber = rs.getString("PHONE_NUMBER");
                String backNumber = phoneNumber.substring(phoneNumber.length() - 4);
                row.put("backNumber", backNumber);
                row.put("department", rs.getString("DEPARTMENT"));
                userList.add(row);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public List<Map<String, Object>> seachAllDoc() {
        //조건없이 결과물 불러오는 sql
//        String sql = "SELECT * FROM PATIENT_TB pt";
        //조건이 있는 결과물 불러오는 sql
        // String sql = "SELECT 가져올컬럼명 FROM sample WHERE 조건걸컬럼명 = ? ";
        //조건이 여러개 있는 결과물 불러오는 sql

        String sql = "SELECT * FROM DOCTOR_TB WHERE del_yn = 'Y'";


        //목록데이터 담을 리스트변수
        List<Map<String, Object>> userList = new ArrayList<>();

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement prsmt = conn.prepareStatement(sql)) {

            //select할때는 excuteQuery() 로 실행 후 ResultSet으로 받아옵니다.
            ResultSet rs = prsmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("DOC_ID", rs.getInt("DOC_ID"));
                row.put("DOC_NAME", rs.getString("DOC_NAME"));
                row.put("DOC_BIRTH", rs.getString("DOC_BIRTH"));
                String phoneNumber = rs.getString("PHONE_NUMBER");
                String backNumber = phoneNumber.substring(phoneNumber.length() - 4);
                row.put("backNumber", backNumber);
                row.put("DEPARTMENT", rs.getString("DEPARTMENT"));
                userList.add(row);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    //해당 번호와 비밀번호를 가진 유저 가져오기
    public int isUser(int userNum, String password) {
        //조건없이 결과물 불러오는 sql
//        String sql = "SELECT * FROM PATIENT_TB pt";
        //조건이 있는 결과물 불러오는 sql
        // String sql = "SELECT 가져올컬럼명 FROM sample WHERE 조건걸컬럼명 = ? ";
        //조건이 여러개 있는 결과물 불러오는 sql
        String sql = "SELECT count(*) as total FROM DOCTOR_TB pt WHERE pt.DOC_ID = ? AND PASSWORD = ? ";

        //해당하는 유저를 담을 변수
        int n = 0;

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement prsmt = conn.prepareStatement(sql)) {
            prsmt.setInt(1, userNum);
            prsmt.setString(2, password);
            //select할때는 excuteQuery() 로 실행 후 ResultSet으로 받아옵니다.
            ResultSet rs = prsmt.executeQuery();

            while (rs.next()) {
                n = rs.getInt("total");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    // 의사 추가 기능
    public void addDoctor(Doctor doctor) {

        String sql = "INSERT INTO DOCTOR_TB (doc_id, doc_name, password, phone_number, doc_birth, department, del_yn) " +

                "VALUES(doctor_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, doctor.getDoc_name());
            pstmt.setString(2, doctor.getPassword());
            pstmt.setString(3, doctor.getPhone_number());
            pstmt.setString(4, doctor.getDoc_birth());
            pstmt.setString(5, doctor.getDepartment().toString());
            pstmt.setString(6, doctor.getDel_yn());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void updateNumberDoctor ( int doc_id, String newPhoneNumber){
        if (newPhoneNumber == null || newPhoneNumber.trim().isEmpty()) {
            System.out.println("새 전화번호는 반드시 입력해야 합니다.");
            return;
        }


        try (Connection conn = DBConnectionManager.getConnection()) {
            String sql = "SELECT phone_number FROM DOCTOR_TB WHERE doc_id = ?";
            try (PreparedStatement checkpstmt = conn.prepareStatement(sql)) {
                checkpstmt.setInt(1, doc_id);
                try (ResultSet rs = checkpstmt.executeQuery()) {
                    if (rs.next()) {
                        String currPhoneNumber = rs.getString("phone_number");
                        if (currPhoneNumber.equals(newPhoneNumber)) {
                            System.out.println("기존 번호와 동일합니다.");
                            return;
                        }
                    }
                }
            }

            sql = "UPDATE DOCTOR_TB SET phone_number = ? WHERE doc_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newPhoneNumber);
                pstmt.setInt(2, doc_id);

                int updatedPhoneNumber = pstmt.executeUpdate();
                System.out.println(updatedPhoneNumber > 0 ? "전화번호 수정완료" : "수정 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePasswordDoctor ( int doc_id, String newPassword){

        if (newPassword == null || newPassword.trim().isEmpty()) {
            System.out.println("새 비밀번호는 반드시 입력해야 합니다.");
            return;
        }
        try (Connection conn = DBConnectionManager.getConnection()) {
            String sql = "SELECT password FROM DOCTOR_TB WHERE doc_id = ?";
            try (PreparedStatement checkpstmt = conn.prepareStatement(sql)) {
                checkpstmt.setInt(1, doc_id);
                try (ResultSet rs = checkpstmt.executeQuery()) {
                    if (rs.next()) {
                        String currPassword = rs.getString("password");
                        if (currPassword.equals(newPassword)) {
                            System.out.println("기존 번호와 동일합니다.");
                            return;
                        }
                    }
                }
            }

            sql = "UPDATE DOCTOR_TB SET password = ? WHERE doc_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newPassword);
                pstmt.setInt(2, doc_id);
                int updatednum = pstmt.executeUpdate();
                System.out.println(updatednum > 0 ? "비밀번호 수정완료" : "수정 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delDoctor(int delUserNum) {
        String sql = "UPDATE DOCTOR_TB SET del_yn = 'N' WHERE DOC_ID = ?";
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, delUserNum);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

