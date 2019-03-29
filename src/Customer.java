import java.util.Date;

public class Customer {
    private int id;
    private String name;
    private String adress;
    private String email;
    private String ccNo;
    private String ccType;
    private java.sql.Date date;

    public Customer(){

    }

    public Customer(int id, String name, String adress, String email, String ccNo, String ccType, java.sql.Date date) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.email = email;
        this.ccNo = ccNo;
        this.ccType = ccType;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCcNo() {
        return ccNo;
    }

    public void setCcNo(String ccNo) {
        this.ccNo = ccNo;
    }

    public String getCcType() {
        return ccType;
    }

    public void setCcType(String ccType) {
        this.ccType = ccType;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }



}
