import java.sql.Date;

public class Payment {
    private int id;
    private java.sql.Date dt;
    private Merchant merchant;
    private Customer customer;
    private String goods;
    private double sumPayed;
    private double chargePayed;

    public Payment(int id, Date dt, Merchant merchant, Customer customer, String goods, double sumPayed) {
        this.id = id;
        this.dt = dt;
        this.merchant = merchant;
        this.customer = customer;
        this.goods = goods;
        this.sumPayed = sumPayed;
        this.chargePayed = Math.round((merchant.getCharge()*sumPayed)/100.0);
    }
    public Payment(Date dt, Merchant merchant, Customer customer, String goods, double sumPayed) {
        this.id = id;
        this.dt = dt;
        this.merchant = merchant;
        this.customer = customer;
        this.goods = goods;
        this.sumPayed = sumPayed;
        this.chargePayed = Math.round((merchant.getCharge()*sumPayed)/100.0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public double getSumPayed() {
        return sumPayed;
    }

    public void setSumPayed(double sumPayed) {
        this.sumPayed = sumPayed;
    }

    public double getChargePayed() {
        return chargePayed;
    }

    public void setChargePayed(double chargePayed) {
        this.chargePayed = chargePayed;
    }
}
