import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class MainClassJDBC {

    public static Connection getConnection() throws SQLException {

        Connection conn=null;
        Properties props = new Properties();
        try (BufferedReader in = Files.newBufferedReader(Paths.get("C:\\Users\\madadm\\IdeaProjects\\MyProject\\app.properties"), Charset.forName("windows-1252"))) {
            props.load(in);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String dbUrl = props.getProperty("dbUrlAddress");
        String userName = props.getProperty("userName");
        String pass = props.getProperty("password");

            conn = DriverManager.getConnection(dbUrl, userName, pass);
            return conn;


    }


    public static void showCustomer(Customer c) {
        System.out.printf("%-20s %-40s %-20s %-20s %-20s %-20s", c.getName(), c.getAdress(), c.getEmail(), c.getCcNo(), c.getCcType(), c.getDate());
        System.out.println();

    }

    public static void showAllMerchant(List<Merchant> m) {

        Iterator it = m.iterator();
        int counter = 0;

        while (it.hasNext()) {
            System.out.printf("%-20s %-40s", m.get(counter).getId(), m.get(counter).getName());
            System.out.println();
            it.next();
            counter++;
        }

    }

    public static void showMenu() {
        System.out.println("0.Exit");
        System.out.println("1.Get customer by ID");
        System.out.println("2.Create customer");
        System.out.println("3.Show customer list");
        System.out.println("4.Delete customer by ID");
        System.out.println("5.Get sum payed to Merchant");
        System.out.println("6.Sort merchants by name");
        System.out.println("7.Add payment");
        System.out.println("8.Create report");

        System.out.print("Enter your choice?");
    }
public static int sum(int x, int y){
        return x+y;
}

    public static void main(String[] args) {





        Connection con = null;
        try {
            con = getConnection();

            Scanner scan = new Scanner(System.in);
            boolean b = true;
            Customer cust = new Customer();
            LocalDate date = LocalDate.now();
            List<Merchant> merchList=CustomerUtils.getMerchants(con);

            while (b) {
                System.out.println();
                showMenu();
                switch (scan.next().charAt(0)) {
                    case '0':
                        b = false;
                        return;
                    case '1':
                        System.out.println();
                        System.out.print("Enter ID?");
                        cust = CustomerUtils.getCustomerById(Integer.valueOf(scan.next()), con);
                        showCustomer(cust);
                        break;
                    case '2':
                        System.out.println();
                        scan.nextLine();
                        System.out.print("Enter name? ");
                        cust.setName(scan.nextLine());
                        System.out.print("Enter address? ");
                        cust.setAdress(scan.nextLine());
                        System.out.print("Enter email address? ");
                        cust.setEmail(scan.nextLine());
                        System.out.print("Enter ccNo? ");
                        cust.setCcNo(scan.nextLine());
                        System.out.print("Enter ccType? ");
                        cust.setCcType(scan.nextLine());
                        cust.setDate(java.sql.Date.valueOf(date));
                        if (CustomerUtils.addCustomer(cust, con) != false) {
                            System.out.println("Successsful");
                        } else {
                            System.out.println("Customer not added");
                        }
                        break;
                    case '3':

                        CustomerUtils.showCustomers(con);
                        break;
                    case '4':
                        System.out.println();
                        scan.nextLine();
                        System.out.print("Enter ID?");
                        if (CustomerUtils.removeCustomer(Integer.valueOf(scan.nextLine()), con) != false) {
                            System.out.println("Customer sucseccfull removed");
                        } else {
                            System.out.println("No customer with this ID");
                        }
                        break;
                    case '5':
                        System.out.println();
                        scan.nextLine();
                        System.out.print("Enter Merchant ID?");
                        CustomerUtils.getSumPayedToMerchant(Integer.valueOf(scan.nextLine()), con);
                        break;
                    case '6':
                        merchList = CustomerUtils.getMerchants(con);
                        merchList.sort(Comparator.comparing(Merchant::getName));
                        showAllMerchant(merchList);
                        break;
                    case '7':
                        System.out.println();
                        scan.nextLine();
                        System.out.print("Enter Merchant ID? ");
                        String in1 = scan.nextLine();
                        System.out.print("Enter sum payed? ");
                        String in2 = scan.nextLine();
                        int res = CustomerUtils.isMinSumOfPayment(Integer.valueOf(in1), Double.valueOf(in2), con);
                        if (res == -1) {
                            System.out.print("No merchants with this ID");
                            break;
                        } else if (res == 0) {
                            System.out.println("Sum payed less than need!!");
                            break;
                        }
                        System.out.print("Enter Customer ID payed? ");
                        String in3 = scan.nextLine();
                        System.out.print("Enter Goods name? ");
                        String in4 = scan.nextLine();

                        if (CustomerUtils.addPayment(new Payment(java.sql.Date.valueOf(LocalDate.now()), merchList.get(Integer.valueOf(in1)+1),
                                CustomerUtils.getCustomerById(Integer.valueOf(in3), con), in4, Double.valueOf(in2)), con)){
                            System.out.println("Succsesfull payment added");
                        }
                        break;
                    case '8':
                        CustomerUtils.createReport(con);
                        break;
                    case 9:
                        System.out.println();
                        scan.nextLine();
                        System.out.print("1? ");
                        String In1 = scan.nextLine();
                        System.out.print("2? ");
                        String In2 = scan.nextLine();
                        int i = sum(Integer.valueOf(In1),Integer.valueOf(In2));
                        System.out.println(i);
                        break;

                }

            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (con !=null)con.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
