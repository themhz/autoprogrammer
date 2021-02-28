/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelcreator;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author themhz
 */
public class MysqlCon {
    
    public static ResultSet getAllTables(Connection con){
        try {
            
            //here sonoo is database name, root is username and password  
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.tables\n" +
                                             "WHERE table_schema = 'erg3_theotokatos';");            
                        
            return rs;
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return null;
    }
    
        public static ResultSet getAllFields(Connection con, String table){
        try {
            
            //here sonoo is database name, root is username and password  
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * \n" +
                                            "  from information_schema.columns \n" +
                                            " where table_schema = 'erg3_theotokatos' \n" +
                                            "   and table_name = '"+table+"'\n" +
                                            "   ;");            
                        
            return rs;
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return null;
    }
    
    
    public static void connect(){  
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/erg3_theotokatos", "root", "");
            //here sonoo is database name, root is username and password  
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from courses");
            while (rs.next()) {
                System.out.println(rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
