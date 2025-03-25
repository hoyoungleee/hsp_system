package repository;

import entity.Doctor;
import entity.Patient;
import jdbc.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorRepository {

    public void updateNumberDoctor(int doc_id, String newPhoneNumber) {
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
                System.out.println(updatedPhoneNumber > 0 ? "비밀번호 수정완료" : "수정 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePasswordDoctor(int doc_id, String newPassword) {

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
}
