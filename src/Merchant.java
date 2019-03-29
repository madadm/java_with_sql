
public class Merchant {
    private int id;
    private String name;
    private String bankName;
    private String swift;
    private String accountNo;
    private double charge;
    private int period;
    private double minSum;
    private double needToSent;
    private double sent;
    private java.sql.Date lastSent;

    public Merchant(){

    }

    public Merchant(int id, String name, String bankName, String swift, String accountNo, double charge,
                    int period, double minSum, double needToSent, double sent, java.sql.Date lastSent) {
        this.id = id;
        this.name = name;
        this.bankName = bankName;
        this.swift = swift;
        this.accountNo = accountNo;
        this.charge = charge;
        this.period = period;
        this.minSum = minSum;
        this.needToSent = needToSent;
        this.sent = sent;
        this.lastSent = lastSent;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public double getMinSum() {
        return minSum;
    }

    public void setMinSum(double minSum) {
        this.minSum = minSum;
    }

    public double getNeedToSent() {
        return needToSent;
    }

    public void setNeedToSent(double needToSent) {
        this.needToSent = needToSent;
    }

    public double getSent() {
        return sent;
    }

    public void setSent(double sent) {
        this.sent = sent;
    }

    public java.sql.Date getLastSent() {
        return lastSent;
    }

    public void setLastSent(java.sql.Date lastSent) {
        this.lastSent = lastSent;
    }

}
