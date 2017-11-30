//MySQL Database Sakila
package catalogue_db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
// or import java.sql.*;

/**
 *
 * @author AsusGL702Vm
 */
public class ConsumeData {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/test_catalogue";

    //Database credentials
    static final String USER = "test_catalogue_user";
    static final String PASS = "test";

    public static void main(String[] args) throws ClassNotFoundException {
        test2();
    }//end main
    
    public static void test2(){
        Connection conn = null;
        Statement stmt = null;


        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Eskase o Driver re malaka!");
        }

        //STEP 3: Open a connection
        System.out.println("Connecting to database...");
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException ex) {
            System.out.println("Eskase h sindesi re malaka!");
        }

        //STEP 4: Execute a query 
        System.out.println("Creating statement...");
        try {
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println("Paei to statement gia vrouves");
        }
        String sql;
        sql = "SELECT * FROM members";
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int memberID = rs.getInt("member_id");
                String firstname = rs.getString("f_name");
                String lastname = rs.getString("l_name");
                String landline = rs.getString("landline");
                String mobile = rs.getString("mobile");

                //Display Values
                System.out.println("ActorID: " + memberID);
                System.out.println(", First name: " + firstname);
                System.out.println(", Last name: " + lastname);
                System.out.println(", Landline phone: " + landline);
                System.out.println(", Mobile phone: " + mobile);
            }
            //STEP 6 : Clean-up enviroment
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Moufa to query...");
        }

        try {
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Tin ekane to statement...");
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Paei i sindesi...");
        }
        System.out.println("Finished!");
    }
    
    
}//end class ConsumeData