package entity;

import java.time.LocalDate;

public class Booking {
    private int booking_id;
    private int user_id;
    private String doc_id;
    private String content;
    private LocalDate date;
    private String status;

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

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
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

    public Booking(int booking_id, int user_id, String doc_id, String content, LocalDate date, String status) {
        this.booking_id = booking_id;
        this.user_id = user_id;
        this.doc_id = doc_id;
        this.content = content;
        this.date = date;
        this.status = status;
    }
}
