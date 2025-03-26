package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Booking {
    private int booking_id;
    private int user_id;
    private int doc_id;
    private String content;
    private LocalDate date;
    private String status;
    private String patientName;
    private String patientBirth;
    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


    public Booking(int booking_id, int user_id, int doc_id,  String content, LocalDate date, String status) {
        this.booking_id = booking_id;
        this.user_id = user_id;
        this.doc_id = doc_id;
        this.content = content;
        this.date = date;
        this.status = status;
    }
    //의사에서 정보 조회 쓰는 생성자
    public Booking(int booking_id, int user_id, int doc_id, LocalDate date, String content, String patientName, String patientBirth) {
        this.booking_id = booking_id;
        this.user_id = user_id;
        this.doc_id = doc_id;
        this.content = content;
        this.date = date;
        this.patientName = patientName;
        this.patientBirth = patientBirth;
    }

    public Booking(int booking_id, int user_id, int doc_id, LocalDate date, String content, String patientName, String patientBirth, String department) {
        this.booking_id = booking_id;
        this.user_id = user_id;
        this.doc_id = doc_id;
        this.date = date;
        this.content = content;
        this.patientName = patientName;
        this.patientBirth = patientBirth;
        this.department = department;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientBirth() {
        return patientBirth;
    }

    public void setPatientBirth(String patientBirth) {
        this.patientBirth = patientBirth;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(int doc_id) {
        this.doc_id = doc_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //예약 생성시 쓰는 생성자
    public Booking(int user_id, int doc_id, String content, LocalDate date, String status) {
        this.user_id = user_id;
        this.doc_id = doc_id;
        this.content = content;
        this.date = date;
        this.status = status;
    }


    @Override
    public String toString() {
        return "Booking{" +
                "booking_id=" + booking_id +
                ", user_id=" + user_id +
                ", doc_id=" + doc_id +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", patientName='" + patientName + '\'' +
                ", patientBirth='" + patientBirth + '\'' +
                ", department=" + Department.valueOf(department).getName() +
                ", charge=" + Department.valueOf(department).getPrice() +
                '}';
    }
}
