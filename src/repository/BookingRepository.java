package repository;

import entity.Booking;
import entity.UserDto;
import jdbc.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static jdbc.DBConnectionManager.getConnection;

public class BookingRepository {
    // Booking 예약 신청
    public boolean addBooking(Booking booking) {
        String insert_booking = "INSERT INTO booking_tb (booking_id, booking_status, user_id, booking_date, content, doc_id) VALUES (BOOKING_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insert_booking)) {

            pstmt.setString(1, booking.getStatus());
            pstmt.setInt(2, booking.getUser_id());
            pstmt.setString(3, booking.getDate().toString());  // LocalDate를 String으로 변환
            pstmt.setString(4, booking.getContent());
            pstmt.setInt(5, booking.getDoc_id());

            int result = pstmt.executeUpdate(); // 실행된 행 수 반환

            return result > 0; // 1 이상이면 성공

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 환자 이름으로 예약 검색
    public List<Booking> getBookingByUser(String userName) {
        List<Booking> bookings = new ArrayList<>();  // 예약 리스트 초기화
        String query = "SELECT b.* FROM booking_tb b JOIN patient_tb p ON b.user_id = p.user_id WHERE p.user_name = ? AND booking_status = 'N'";  // 특정 환자만 검색

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userName);  // 사용자 이름을 파라미터로 설정

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Booking 객체를 생성하여 리스트에 추가
                    Booking booking = new Booking(
                            rs.getInt("booking_id"),             // 예약 ID
                            rs.getInt("user_id"),                // 사용자 ID
                            rs.getInt("doc_id"),                 // 의사 ID
                            rs.getString("content"),             // 예약 내용
                            LocalDate.parse(rs.getString("booking_date"), DateTimeFormatter.ISO_DATE), // 예약 날짜
                            rs.getString("booking_status")      // 예약 상태
                    );
                    bookings.add(booking);  // 리스트에 예약 추가
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;  // 예약 리스트 반환
    }

    // 의사 이름으로 예약 검색
    public List<Booking> findBookingByDoctorName(String doctorName) {
        List<Booking> bookings = new ArrayList<>();
        String query = """
                SELECT b.*, p.user_name as userName , p.user_birth as userBirth
                FROM booking_tb b 
                JOIN patient_tb p ON b.user_id = p.user_id 
                JOIN doctor_tb d ON b.doc_id = d.doc_id 
                WHERE d.doc_name = ? AND booking_status = 'N'
            """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, doctorName);  // 의사 이름을 파라미터로 설정

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Booking 객체를 생성하여 리스트에 추가
                    Booking booking = new Booking(
                            rs.getInt("booking_id"),             // 예약 ID
                            rs.getInt("user_id"),                // 사용자 ID
                            rs.getInt("doc_id"),                 // 의사 ID
                            LocalDate.parse(rs.getString("booking_date"), DateTimeFormatter.ISO_DATE), // 예약 날짜
                            rs.getString("content"),             // 예약 내용
                            rs.getString("userName"),
                            rs.getString("userBirth")
                    );
                    bookings.add(booking);  // 리스트에 예약 추가
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;  // 예약 리스트 반환
    }

    // Booking 삭제
    public boolean deleteBooking(int bookingId) {
        String delete_booking = "DELETE FROM booking_tb WHERE booking_id=?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(delete_booking)) {

            pstmt.setInt(1, bookingId);
            int result = pstmt.executeUpdate();
            return result > 0;  // 삭제 성공 여부 반환

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
//    public List<Booking> FindbyBookingCharge(int patientId) {
//        List<Booking> bookingchargeList = new ArrayList<>();
//        String query = "SELECT b.*, d.department, p.*" +
//                "FROM booking_tb b " +
//                "JOIN doctor_tb d ON b.doc_id = d.doc_id " +
//                "JOIN patient_tb p ON b.user_id = p.user)id " +
//                "WHERE b.booking_status = 'Y' AND b.user_id = ? ";
//
//        try (Connection connection = getConnection();
//             PreparedStatement pstmt = connection.prepareStatement(query)) {
//
//            pstmt.setInt(1, patientId);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                while (rs.next()) {
//                    Booking booking = new Booking(
//                            rs.getInt("b.booking_id"),
//                            rs.getInt("b.user_id"),
//                            rs.getInt("d.doc_id"),
//                            rs.getDate("b.booking_date").toLocalDate(),
//                            rs.getString("b.content"),
//                            rs.getString("p.user_name"),
//                            rs.getString("p.user_birth")
//                    );
//
//
//                    // 상태값 설정
//                    booking.setStatus(rs.getString("b.booking_status"));
//
//                    bookingchargeList.add(booking);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return bookingchargeList;
//    }

    public List<Booking> FindbyBookingCharge(int patientId) {
        List<Booking> bookingchargeList = new ArrayList<>();

        // 쿼리 수정: JOIN 문법 오류 수정 및 컬럼명 명시
        String query = "SELECT b.booking_id AS booking_id, b.user_id AS user_id, b.booking_date AS booking_date, b.booking_status AS booking_status, b.content AS content, " +
                "d.doc_id AS doc_id, d.department AS department, " +
                "p.user_name AS user_name, p.user_birth AS user_birth " +
                "FROM booking_tb b " +
                "JOIN doctor_tb d ON b.doc_id = d.doc_id " +
                "JOIN patient_tb p ON b.user_id = p.user_id " +  // 괄호 오류 수정
                "WHERE b.booking_status = 'Y' AND b.user_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, patientId); // patientId를 조건으로 설정

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking(
                            rs.getInt("booking_id"),
                            rs.getInt("user_id"),
                            rs.getInt("doc_id"),
                            rs.getDate("booking_date").toLocalDate(),
                            rs.getString("content"),
                            rs.getString("user_name"),
                            rs.getString("user_birth"),
                            rs.getString("department")
                    );

                    // 상태값 설정
                    booking.setStatus(rs.getString("booking_status"));

                    // 예약 리스트에 추가
                    bookingchargeList.add(booking);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bookingchargeList;
    }


    public void insertContent(int bookingId, String content) {
        String sql = "UPDATE booking_tb SET content = ?, booking_status = 'Y' WHERE booking_id = ? ";

        try (Connection conn = DBConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, content);
            pstmt.setInt(2, bookingId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
