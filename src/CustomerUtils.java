import java.io.*;
import java.sql.*;
import java.util.*;

public class CustomerUtils {



    public static Customer getCustomerById(int extId, Connection con) {

        Customer tCustomer = null;
        ResultSet rs;
        try (PreparedStatement stmnt = con.prepareStatement("SELECT * FROM customer WHERE id =?")) {

            stmnt.setInt(1, extId);
            rs = stmnt.executeQuery();
            if (rs.next()) {
                tCustomer = new Customer();
                tCustomer.setId(rs.getInt("id"));
                tCustomer.setName(rs.getString("name"));
                tCustomer.setAdress(rs.getString("address"));
                tCustomer.setEmail(rs.getString("email"));
                tCustomer.setCcNo(rs.getString("ccNo"));
                tCustomer.setCcType(rs.getString("ccType"));
                tCustomer.setDate(rs.getDate("maturity"));
            }

        } catch (Exception e) {
            System.out.println("Error to get Customer by ID");
            e.printStackTrace();
        }
        return tCustomer;
    }

    public static boolean updateCustomer(Customer c, Connection conn) {

        String sql = "UPDATE customer SET name=?, address=?, ";
        sql += " email=?, ccNo=?, ccType=?, maturity=? WHERE id=?";

        java.sql.Date dt = new java.sql.Date(c.getDate().getTime());
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, c.getId());
            stmt.setString(2, c.getName());
            stmt.setString(3, c.getAdress());
            stmt.setString(4, c.getEmail());
            stmt.setString(5, c.getCcNo());
            stmt.setString(6, c.getCcType());
            stmt.setDate(7, dt);
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean removeCustomer(int id, Connection conn) {

        String sql = "DELETE from customer WHERE id=" + id;
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public static void showCustomers(Connection conn) {
        String sql = "SELECT * FROM customer";
        Statement stmt;
        try {
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.printf("%-5s %-20s %-50s %-30s %-20s %-20s %-20s\n", rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
            }
            System.out.println();
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean addCustomer(Customer c, Connection con) {


        String sql = "INSERT INTO customer (name, address, ";
        sql += " email, ccNo, ccType, maturity) values(?,?,?,?,?,?) ";
        PreparedStatement stmt;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, c.getName());
            stmt.setString(2, c.getAdress());
            stmt.setString(3, c.getEmail());
            stmt.setString(4, c.getCcNo());
            stmt.setString(5, c.getCcType());
            stmt.setDate(6, c.getDate());
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void getSumPayedToMerchant(int id, Connection conn) {

        String sql = "SELECT sum(p.sumPayed) ";
        sql += "FROM payment p INNER JOIN merchant m ON m.id=p.merchantID ";
        sql += ("WHERE m.id=" + String.valueOf(id));
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("Sum payed to Merchant with ID=" + String.valueOf(id) + ": " + rs.getString(1));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Merchant> getMerchants(Connection conn) {

        List<Merchant> tmpList = new ArrayList<>();
        try {
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM merchant");
            while (rs.next()) {
                tmpList.add(new Merchant(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getDouble(6), rs.getInt(7),
                        rs.getDouble(8), rs.getDouble(9), rs.getDouble(10), rs.getDate(11)));
            }
            rs.close();
            stmnt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmpList;
    }

    public static boolean addPayment(Payment payment, Connection conn) {

        boolean result = false;
        String sql = "INSERT INTO payment (dt, merchantId, ";
        sql += " customerID, goods, sumPayed, chargePayed) values(?,?,?,?,?,?) ";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, payment.getDt());
            stmt.setInt(2, payment.getMerchant().getId());
            stmt.setInt(3, payment.getCustomer().getId());
            stmt.setString(4, payment.getGoods());
            stmt.setDouble(5, payment.getSumPayed());
            stmt.setDouble(6, payment.getChargePayed());
            result = stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int isMinSumOfPayment(int merchantID, double sum, Connection conn) {

        String sql = "SELECT minSum from merchant where id=?";
        int result = -1;


        try (PreparedStatement stmnt = conn.prepareStatement(sql)){

            stmnt.setInt(1, merchantID);
            ResultSet rs = stmnt.executeQuery();

            if (rs.next()) {
                if (rs.getDouble(1) <= sum) {
                    result = 1;
                } else {
                    result = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void createReport(Connection conn) {

        String sql = "select (select count(*)from merchant),(select count(*)from customer),sum(p.sumPayed),(select count(*)from payment),sum(p.chargePayed) ";
        sql += "from payment p";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                try (Formatter fout = new Formatter(new FileWriter("report.txt"))) {
                    fout.format("%-40s %-40s %-40s %-40s %-40s\n", "Quantity of Merchants", "Quantity of Customers", "Sum payed by customers", "Number of payments made","Charge get by merchants");
                    fout.format("%-40s %-40s %-40s %-40s %-40s", rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getInt(4), rs.getDouble(5));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            rs.close();
            st.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}