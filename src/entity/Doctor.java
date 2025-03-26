package entity;

public class Doctor {
    private int doc_id;
    private String doc_name;
    private String password;
    private String  phone_number;
    private String doc_birth;
    private String del_yn;
    private Department department;


    //계정생성할떄 id가 없기 때문에 생성할때 id값을 뺌
    public Doctor( String doc_name, String password, String phone_number, String doc_birth, String del_yn, Department department) {
        this.doc_name = doc_name;
        this.password = password;
        this.phone_number = phone_number;
        this.doc_birth = doc_birth;
        this.department = department;
        this.del_yn = del_yn;
    }

    //일반 정보를 담을 때 씀.
    public Doctor(int doc_id, String doc_name, String password, String phone_number, String doc_birth, String del_yn, Department department) {
        this.doc_id = doc_id;
        this.doc_name = doc_name;
        this.password = password;
        this.phone_number = phone_number;
        this.doc_birth = doc_birth;
        this.department = department;
        this.del_yn = del_yn;
    }

    public int getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(int doc_id) {
        this.doc_id = doc_id;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDoc_birth() {
        return doc_birth;
    }

    public void setDoc_birth(String doc_birth) {
        this.doc_birth = doc_birth;
    }

    public String getDel_yn() {
        return del_yn;
    }

    public void setDel_yn(String active) {
        this.del_yn = active;
    }

    public Department getDepartment() {
        return department;
    }


}
