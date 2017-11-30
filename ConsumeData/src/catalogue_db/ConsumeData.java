package catalogue_db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author padoura
 */
public class ConsumeData {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/test_catalogue";

    //Database credentials
    static final String USER = "test_catalogue_user";
    static final String PASS = "test";
    static Connection conn = null;
    static Statement stmt = null;
    static ResultSet rs = null;

    public static void main(String[] args){
        if (test2())
            mainMenu();
        else
            System.out.println("Database could not be reached. Please ask your database administrator."); 
    }//end main
    
    public static void mainMenu() {
		Scanner menuScanner = new Scanner(System.in);
		int choice = -1;
		
		while(choice != 0){
			try{
				printMenu();
				choice = menuScanner.nextInt();
				menuScanner.nextLine();
				switch (choice) {
				case 1: insertTable();
						break;
				case 2: searchUser();
						break;
				case 3: updateLandline();
						break;
				case 4: countMembers();
						break;
                                case 5: createBirthdayTable();
                                        break;
                                case 8: resetDb();
                                                break;
				case 0: terminate();
						break;
				default: System.out.println("Choice out of range! Please type from 0 to 8!");
				}
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid input! Please type a number from 0 to 8!");
				menuScanner.nextLine();
			}
		}
    }
    
    public static boolean test2(){
        connect();

        //STEP 4: Execute a query 
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println("Paei to statement gia vrouves");
            return false;
        }
        String sql;
        sql = "SELECT * FROM members";
        try {
            rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                User user = new User();
                user.id = rs.getInt("member_id");
                user.firstname = rs.getString("f_name");
                user.lastname = rs.getString("l_name");
                user.landline = rs.getString("landline");
                user.mobile = rs.getString("mobile");
            }
            rs.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Moufa to query...");
            return false;
        } finally{
            //STEP 6 : Clean-up enviroment
            closeConnection();
        }
    }
    
    public static void insertTable(){
        connect();

        //STEP 4: Execute a query 
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println("Paei to statement gia vrouves");
        }
        String sql;
        sql = "INSERT INTO members (f_name, l_name, landline, mobile) "
                + "VALUES ('Alex', 'Alexiadi', 2110002000, 6989320382),"
                + "('Mike', 'Michailidi', 2110000201, 6989320383),"
                + "('Antonia', 'Antoniadi', 2110000201, 6989320383),"
                + "('Lia', 'Georgiadi', 2110000401, 6987320383),"
                + "('Ria', 'Riadi', 2110000301, 6987320383);";
        try {
        int rs = stmt.executeUpdate(sql);
        System.out.println("Sample data successfully inserted!");
            //STEP 6 : Clean-up enviroment
        } catch (SQLException ex) {
            System.out.println("Moufa to query...");
        }
        
        closeConnection();
    }
    
    public static void searchUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please give your Last Name:");
        String lastname = scanner.nextLine();
        System.out.println("Please give your First Name:");
        String firstname = scanner.nextLine();
        
        connect();

        //STEP 4: Execute a query 
        System.out.println("Creating statement...");
        String sql;
        sql = "SELECT * FROM members WHERE l_name = ? AND f_name = ?";
        try {
            stmt = conn.prepareStatement(sql);
            ((PreparedStatement) stmt).setString(1, lastname);
            ((PreparedStatement) stmt).setString(2, firstname);
        } catch (SQLException ex) {
            System.out.println("Paei to statement gia vrouves");
        }
        try {
            
            rs = ((PreparedStatement) stmt).executeQuery();
            //STEP 5: Extract data from result set
            if (rs.next()) {
                //Retrieve by column name
                User user = new User();
                user.id = rs.getInt("member_id");
                user.firstname = rs.getString("f_name");
                user.lastname = rs.getString("l_name");
                user.landline = rs.getString("landline");
                user.mobile = rs.getString("mobile");

                //Display Values
                System.out.println("- ActorID: " + user.id);
                System.out.println("- First name: " + user.firstname);
                System.out.println("- Last name: " + user.lastname);
                System.out.println("- Landline phone: " + user.landline);
                System.out.println("- Mobile phone: " + user.mobile);
                
            }else{
                System.out.println("No such user exists!");
            }
            //STEP 6 : Clean-up enviroment
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Moufa to query...");
        }
        closeConnection();
    }
    
    public static void updateLandline(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please give your Last Name:");
        String lastname = scanner.nextLine();
        System.out.println("Please give your new landline phone:");
        String landline = scanner.nextLine();
        
        connect();

        //STEP 4: Execute a query 
        System.out.println("Creating statement...");
        String sql;
        sql = "UPDATE members SET landline = ? WHERE l_name = ? LIMIT 1";
        try {
            stmt = conn.prepareStatement(sql);
            ((PreparedStatement) stmt).setString(1, landline);
            ((PreparedStatement) stmt).setString(2, lastname);
        } catch (SQLException ex) {
            System.out.println("Paei to statement gia vrouves");
        }
        int rs;
        try {
            rs = ((PreparedStatement) stmt).executeUpdate();
            //STEP 5: Extract data from result set
            if (rs > 0) {
                System.out.println("Landline phone successfully updated!");
            }else{
                System.out.println("No such user exists!");
            }
        } catch (SQLException ex) {
            System.out.println("Moufa to query...");
        }
        closeConnection();
    }
    
   public static void countMembers(){
        connect();

        //STEP 4: Execute a query 
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println("Paei to statement gia vrouves");
        }
        String sql;
        sql = "SELECT count(*) AS count FROM members";
        try {
            rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            if (rs.next()) {
                //Retrieve by column name
                int count = rs.getInt("count");

                //Display Values
                System.out.println("Total number of members: " + count);
            }
            //STEP 6 : Clean-up enviroment
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Moufa to query...");
        }

        closeConnection();
    }

    private static void terminate() {
        System.out.println("Bye!");
    }
   
    private static void printMenu() {
            System.out.println("Please choose one of the following options:");
            System.out.println("(1) Insert Sample Data");
            System.out.println("(2) Find Member");
            System.out.println("(3) Update Landline Phone");
            System.out.println("(4) Check Total Number of Members");
            System.out.println("(5) Create Table Birthdays");
            System.out.println("(6) Insert All birthdays");
            System.out.println("(7) Find People Per birthday");
            System.out.println("(8) Reset Database");
            System.out.println("(0) Exit");
    }
    
    private static void connect(){
        int flag = 0;
        while(flag < 100){
            String event = tryConnection();
            switch (event) {
                case "ok": flag = 100;
                            break;
                case "driver": System.out.println("No MySQL driver found. Please install it.");
                                flag = 100;
                                break;
                case "db": System.out.println("Database connection problem! Retrying connection...");
                            flag++;
            }
        }
    }
    
    private static String tryConnection(){
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            return "driver";
        }

        //STEP 3: Open a connection
        System.out.println("Connecting to database...");
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException ex) {
            return "db";
        } 
        return "ok";
    }
    
    private static void closeConnection(){
        try {
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Tin ekane to statement...");
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println("Paei i sindesi...");
            }
            System.out.println("Connection finished!");
        }
    }
    
    private static void resetDb(){
        connect();
        
        //STEP 4: Execute a query 
        System.out.println("Creating statements...");
        String sql;
        sql = "DROP TABLE members;";
        try {
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println("Paei to statement gia vrouves");
            closeConnection();
        }
        
        try {
            stmt.addBatch(sql);
            sql = "CREATE TABLE members (" +
                "member_id int AUTO_INCREMENT," +
                " f_name text," +
                " l_name text," +
                " landline text," +
                " mobile text," +
                " PRIMARY KEY (member_id)" +
                ");";
            stmt.addBatch(sql);
            sql = "INSERT INTO members (f_name, l_name, landline, mobile)" +
                   " VALUES ('Alex', 'Alexiadis', 2100002000, 6979320382)," +
                    "('Mike', 'Michailidis', 2100000201, 6979320383)," +
                    "('Antonis', 'Antoniadis', 2100000201, 6979320383);";
            stmt.addBatch(sql);
            stmt.executeBatch();
        } catch (SQLException ex) {
            System.out.println("Moufa to query...");
        }finally{
            closeConnection();
        }
    }

    private static void createBirthdayTable() {
        
        
        String sql = "CREATE TABLE birthdays (" +
            "member_id int AUTO_INCREMENT," +
            " f_name text," +
            " l_name text," +
            " landline text," +
            " mobile text," +
            " PRIMARY KEY (member_id)" +
            ");";
    }
    
    
    

    private static class User {
        
        protected int id;
        protected String firstname;
        protected String lastname;
        protected String landline;
        protected String mobile;
        public User() {
        }
    }
    
    
    
    
}//end class ConsumeData