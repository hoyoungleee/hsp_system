package entity;

public class UserDto {

    private int userId; //회원 고유번호
    private String loginYn = "N";
    private String name;
    private String birth;



    public UserDto(){
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLoginYn() {
        return loginYn;
    }

    public void setLoginYn(String loginYn) {
        this.loginYn = loginYn;
    }

    @Override
    public String toString() {
        return "User{" +
                "회원번호 = " + userId +
                ", 로그인여부 = '" + loginYn + '\'' +
                '}';
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
