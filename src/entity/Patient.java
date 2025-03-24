package entity;

public class Patient {
    private String password;
    private String user_name;
    private String phone_number;
    private String active;
    private String user_birth;
    private int  user_id;

    public Patient(String password, String user_name, String active, int user_id, String phone_number, String user_birth) {
        this.password = password;
        this.user_name = user_name;
        this.active = active;
        this.user_id = user_id;
        this.phone_number = phone_number;
        this.user_birth = user_birth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_birth() {
        return user_birth;
    }

    public void setUser_birth(String user_birth) {
        this.user_birth = user_birth;
    }
}
