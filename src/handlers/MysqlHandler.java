/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import autoprogrammer.MySqlField;
import autoprogrammer.MySqlTable;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author themhz
 */
public class MysqlHandler {

    private static Connection con;

    public static ArrayList<MySqlTable> getAllTables() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/erg3_theotokatos", "root", "");
            //here sonoo is database name, root is username and password  
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.tables\n"
                    + "WHERE table_schema = 'erg3_theotokatos';");

            ArrayList<MySqlTable> tables = new ArrayList<>();
            while (rs.next()) {
                MySqlTable table = new MySqlTable(rs.getString("TABLE_NAME"));
                tables.add(table);
            }

            con.close();
            return tables;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public static MySqlTable getAllFields(String tablename) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/erg3_theotokatos", "root", "");
            //here sonoo is database name, root is username and password  
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * \n"
                    + "  from information_schema.columns \n"
                    + " where table_schema = 'erg3_theotokatos' \n"
                    + "   and table_name = '" + tablename + "'\n"
                    + "   ;");

            MySqlTable table = new MySqlTable(tablename);
            ArrayList<MySqlField> fields = new ArrayList<>();
            while (rs.next()) {
                //String name, String datatype, String size, String foreintable, String isprimary, String isforeinkey
                MySqlField field = new MySqlField(rs.getString("COLUMN_NAME"), rs.getString("DATA_TYPE"), rs.getString("CHARACTER_MAXIMUM_LENGTH"), "", rs.getString("COLUMN_KEY"), "", "");
                fields.add(field);

            }
            table.setFields(fields);
            con.close();
            return table;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }


    public static ArrayList<MySqlTable> getAllTablesWithFields() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/erg3_theotokatos", "root", "");
            //here sonoo is database name, root is username and password  
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.tables\n"
                    + "WHERE table_schema = 'erg3_theotokatos';");

            ArrayList<MySqlTable> tables = new ArrayList<>();
            while (rs.next()) {
                MySqlTable table = new MySqlTable(rs.getString("TABLE_NAME"));
                
                tables.add(table);
            }

            con.close();
            return tables;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
    
    
     public static ArrayList<MySqlField> getTableFields(String tablename) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/erg3_theotokatos", "root", "");
            //here sonoo is database name, root is username and password  
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select a.*,c.REFERENCED_TABLE_NAME,c.REFERENCED_COLUMN_NAME\n" +
                                        "       from information_schema.columns a \n" +
                                        "    left join (SELECT  b.* FROM information_schema.key_column_usage b \n" +
                                        "				WHERE b.CONSTRAINT_SCHEMA = 'erg3_theotokatos' \n" +
                                        "				and b.table_schema = 'erg3_theotokatos'\n" +
                                        "				and b.table_name = '"+tablename+"' and b.referenced_table_name is not null) c on c.column_name = a.column_name\n" +
                                        "   where a.table_schema = 'erg3_theotokatos' and a.table_name = '"+tablename+"'\n" +
                                        "   order by a.ORDINAL_POSITION");

            
            ArrayList<MySqlField> fields = new ArrayList<>();
            while (rs.next()) {
                //String name, String datatype, String size, String foreintable, String isprimary, String isforeinkey
                MySqlField field = new MySqlField(rs.getString("COLUMN_NAME"), rs.getString("DATA_TYPE"), rs.getString("CHARACTER_MAXIMUM_LENGTH"), rs.getString("REFERENCED_TABLE_NAME"), rs.getString("COLUMN_KEY"), rs.getString("REFERENCED_COLUMN_NAME"), rs.getString("IS_NULLABLE"));
                fields.add(field);

            }            
            con.close();
            return fields;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
}
