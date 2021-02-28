/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelcreator;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author themhz
 */
public class Modelcreator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
         createModels();
        //FileHandler.createFile();
        //FileHandler.openFile("C:\\wamp64\\www\\testphp\\test2.php");
    }
    
    
    
    
    public static void createModels() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/erg3_theotokatos", "root", "");
        ResultSet rs = MysqlCon.getAllTables(con);
        
        String fields = "";
        String table = "";
        String values = "";
        String setvalues = "";        
        String selectarray = "";
        String updatecols = "";
        String updatevalues = "";
        
        
        int counter=0;
        int selectcounter=0;
        while (rs.next()) {
            //Παίρνει όλους τους πίνακες
            //System.out.println(rs.getString("TABLE_NAME"));
            table = rs.getString("TABLE_NAME");
            //Για κάθε πίνακα πηγαίνει και διαβάζει όλα τα παιδία
            
            ResultSet rs2 = MysqlCon.getAllFields(con, table);
            //System.out.println("---------------------------------------");
            while (rs2.next()) {                
               // System.out.println(rs2.getString("COLUMN_NAME")+ " " + rs2.getString("COLUMN_TYPE"));                
                //System.out.println(rs2.getString("COLUMN_NAME")+ " " + rs2.getString("EXTRA"));
                
                if(!rs2.getString("EXTRA").toString().equals("auto_increment")){
                    //create insert fields
                    if(counter == 0){

                        fields=rs2.getString("COLUMN_NAME");
                        values=":"+rs2.getString("COLUMN_NAME");                        
                        updatecols = rs2.getString("COLUMN_NAME")+"=:" + rs2.getString("COLUMN_NAME");
                    }else{
                        fields+=","+rs2.getString("COLUMN_NAME");
                        values+=",:"+rs2.getString("COLUMN_NAME");
                        updatecols += "," + rs2.getString("COLUMN_NAME")+"=:"+rs2.getString("COLUMN_NAME");
                    }
                    
                    
                    //return selected fields
                    setvalues +="$values[\":"+rs2.getString("COLUMN_NAME")+"\"] = $obj->"+rs2.getString("COLUMN_NAME")+";\n\t";
                    
                    updatevalues +="$values[\":"+rs2.getString("COLUMN_NAME")+"\"] = $obj->"+rs2.getString("COLUMN_NAME")+";\n\t";
                    
                    
                    counter++;  
                }
                
                 if(selectcounter == 0){
                    selectarray = "'"+rs2.getString("COLUMN_NAME")+"' => $row->"+rs2.getString("COLUMN_NAME")+"\n\t\t";
                 }else{
                     selectarray += ",'"+rs2.getString("COLUMN_NAME")+"' => $row->"+rs2.getString("COLUMN_NAME")+"\n\t\t";
                 }
                 
                 selectcounter++;
            }
            
            selectcounter = 0;
            counter = 0;            
                      
            String filecontents = FileHandler.openFile("model_sample.template");
            //System.out.println(filecontents);
            System.out.println("for " + table);
            filecontents = filecontents.replace("{entity}", table.substring(0, 1).toUpperCase() + table.substring(1));
            filecontents = filecontents.replace("{table}", table);
            filecontents = filecontents.replace("{fields}", fields);
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
            FileHandler.createFile("C:\\wamp64\\www\\testphp\\" + table, filecontents);
            //Για κάθε ένα αρχείο
            
            
        }
        
        con.close();
    }
    
}
