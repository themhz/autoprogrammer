/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoprogrammer;

import handlers.MysqlHandler;
import handlers.FileHandler;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Field;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author themhz
 */
public class AutoProgrammer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
         //createModels();
        //FileHandler.createFile();
        //FileHandler.openFile("C:\\wamp64\\www\\testphp\\test2.php");
        createViewsAndControllers();
    }
    
    
    
    public static ArrayList<MySqlTable> createViewsAndControllers() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/erg3_theotokatos", "root", "");
        //ResultSet rsTables = MysqlHandler.getAllTables();
        ArrayList<MySqlTable> tables = MysqlHandler.getAllTables();
                
        
                        
        for(MySqlTable table :tables){                        
            ArrayList<MySqlField> fields = MysqlHandler.getAllFields(table.getName()).getFields();
            table.setFields(fields);
        }
        
        return tables;
    }
    
    
    public static void createModels() throws SQLException, ClassNotFoundException {
        
        ArrayList<MySqlTable> tables = MysqlHandler.getAllTables();

        String fieldname = "";
//        String table = "";
        String values = "";
        String setvalues = "";        
        String selectarray = "";
        String updatecols = "";
        String updatevalues = "";
        
        
        int counter=0;
        int selectcounter=0;
        //while (rs.next()) {
        for(MySqlTable table: tables){
            //Παίρνει όλους τους πίνακες
            //System.out.println(rs.getString("TABLE_NAME"));
            //table = rs.getString("TABLE_NAME");
            //Για κάθε πίνακα πηγαίνει και διαβάζει όλα τα παιδία
            ArrayList<MySqlField> fields = MysqlHandler.getTableFields(table.getName());
                     //= MysqlHandler.getAllFields(table.getName());
            //System.out.println("---------------------------------------");
            //while (rs2.next()) {                
            for(MySqlField field :fields){
               // System.out.println(rs2.getString("COLUMN_NAME")+ " " + rs2.getString("COLUMN_TYPE"));                
                //System.out.println(rs2.getString("COLUMN_NAME")+ " " + rs2.getString("EXTRA"));
                
                if(!field.getIsprimary().equals("PRI")){
                    //create insert fields
                    if(counter == 0){

                        fieldname = field.getName();
                        values=":"+field.getName();                        
                        updatecols = field.getName()+"=:" + field.getName();
                    }else{
                        fieldname+=","+field.getName();
                        values+=",:"+field.getName();
                        updatecols += "," + field.getName()+"=:"+field.getName();
                    }
                    
                    
                    //return selected fields
                    setvalues +="$values[\":"+field.getName()+"\"] = $obj->"+field.getName()+";\n\t";
                    
                    updatevalues +="$values[\":"+field.getName()+"\"] = $obj->"+field.getName()+";\n\t";
                    
                    
                    counter++;  
                }
                
                 if(selectcounter == 0){
                    selectarray = "'"+field.getName()+"' => $row->"+field.getName()+"\n\t\t";
                 }else{
                     selectarray += ",'"+field.getName()+"' => $row->"+field.getName()+"\n\t\t";
                 }
                 
                 selectcounter++;
            }
            
            selectcounter = 0;
            counter = 0;            
                      
            String filecontents = FileHandler.openFile("model_sample.template");
            //System.out.println(filecontents);
            System.out.println("for " + table);
            filecontents = filecontents.replace("{entity}", table.getName().substring(0, 1).toUpperCase() + table.getName().substring(1));
            filecontents = filecontents.replace("{table}", table.getName());
            filecontents = filecontents.replace("{fields}", fieldname);
            filecontents = filecontents.replace("{values}", values);
            filecontents = filecontents.replace("{setvalues}", setvalues);
            filecontents = filecontents.replace("{selectarray}", selectarray);
            filecontents = filecontents.replace("{updatecols}", updatecols);
            filecontents = filecontents.replace("{updatevalues}", updatevalues);
            
            
            updatecols = "";
            updatevalues = "";
            setvalues = "";            
            System.out.println(filecontents);
            System.out.println("-----------------------");
            FileHandler.createFile("C:\\wamp64\\www\\testphp\\" + table.getName(), filecontents);
            //Για κάθε ένα αρχείο
            
            
        }
        
        //
    }
    
}
