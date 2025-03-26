package entity;

public class Patient {
    private String password;
    private String user_name;
    private String phone_number;
    private String del_yn;
    private int  user_id;
    private String  user_brith;



    //계정생성할떄 id가 없기 때문에 생성할때 id값을 뺌
    public Patient(String password, String user_name, String phone_number, String del_yn, String user_brith) {
        this.password = password;
        this.user_name = user_name;
        this.phone_number = phone_number;
        this.del_yn = del_yn;
        this.user_brith = user_brith;
    }

    //일반 정보를 담을 때 씀.
    public Patient(String password, String user_name, String phone_number, String del_yn, int user_id, String user_brith) {
        this.password = password;
        this.user_name = user_name;
        this.phone_number = phone_number;
        this.del_yn = del_yn;
        this.user_id = user_id;
        this.user_brith = user_brith;
    }

    public String getUser_brith() {
        return user_brith;
    }

    public void setUser_brith(String user_brith) {
        this.user_brith = user_brith;
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

    public String getDel_yn() {
        return del_yn;
    }

    public void setDel_yn(String del_yn) {
        this.del_yn = del_yn;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
