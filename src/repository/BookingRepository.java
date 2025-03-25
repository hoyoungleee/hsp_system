package repository;

import entity.Booking;
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
    private  Booking booking ;
    // Booking 예약 신청
    public boolean addBooking() {
        String insert_booking = "INSERT INTO booking_tb (booking_id, booking_status, user_id, booking_date, content, doc_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insert_booking)) {

            pstmt.setInt(1, booking.getBooking_id());
            pstmt.setString(2, booking.getStatus());
            pstmt.setInt(3, booking.getUser_id());
            pstmt.setString(4, String.valueOf(booking.getDate()));
            pstmt.setString(5, booking.getContent());
            pstmt.setInt(6, booking.getDoc_id());

            int result = pstmt.executeUpdate(); // 실행된 행 수 반환
            return result > 0; // 1 이상이면 성공

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
   //booking 환자 검색용
    public List<Booking> getBookingByUser(String userName ) {
        List<Booking> bookings = new ArrayList<>();  ///리스트에 넣고
        String query = "SELECT b.* FROM booking_tb b JOIN patient_tb p ON b.user_id = p.user_id WHERE p.user_name = ?";  //특정 환자만 검색
         try(Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
             pstmt.setInt(1, booking.getBooking_id());
             pstmt.setString(2, booking.getStatus());
             pstmt.setInt(3, booking.getUser_id());
             pstmt.setString(4, String.valueOf(booking.getDate()));
             pstmt.setString(5, booking.getContent());
             pstmt.setInt(6, booking.getDoc_id());
         }catch(SQLException e){
             e.printStackTrace();
         }
      return bookings;
    }
  // 의사 검색용
public List<Booking> findBookingByByDoctorName(String DoctorName) {
        List<Booking> bookings = new ArrayList<>();
    String query = """
            SELECT b.*, p.user_name 
            FROM booking_tb b 
            JOIN patient_tb p ON b.user_id = p.user_id 
            JOIN doctor_tb d ON b.doc_id = d.doc_id 
            WHERE d.doc_name = ?
        """;

    try(Connection conn = getConnection();
    PreparedStatement preparedStatement = conn.prepareStatement(query)){
     preparedStatement.setString(1, DoctorName);
     try ( ResultSet rs = preparedStatement.executeQuery()){
         while (rs.next()) {
             Booking booking = new Booking(
                     rs.getInt("booking_id"),             // 예약 ID
                     rs.getString("booking_status"),      // 예약 상태
                     rs.getInt("user_id"),                // 사용자 ID
                     LocalDate.parse(rs.getString("booking_date"), DateTimeFormatter.ISO_DATE), // 예약 날짜를 LocalDate로 변환
                     rs.getString("content"),             // 예약 내용
                     rs.getString("doc_id")               // 의사 ID
             );
             bookings.add(booking);  // 리스트에 예약 추가
         }
     }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    return bookings;
}



    // Booking 삭제
    public boolean deleteBooking(int bookingId) {
        String delete_booking = "DELETE FROM booking_tb WHERE booking_id=?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(delete_booking)) {

            pstmt.setInt(1, bookingId);
            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
