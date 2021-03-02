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
import javax.swing.JTable;

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

    public static ArrayList<MySqlTable> createViewsAndControllers() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/erg3_theotokatos", "root", "");

        ArrayList<MySqlTable> tables = MysqlHandler.getAllTables();

        for (MySqlTable table : tables) {
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

        int counter = 0;
        int selectcounter = 0;

        for (MySqlTable table : tables) {

            ArrayList<MySqlField> fields = MysqlHandler.getTableFields(table.getName());

            for (MySqlField field : fields) {

                if (!field.getIsprimary().equals("PRI")) {
                    //create insert fields
                    if (counter == 0) {

                        fieldname = field.getName();
                        values = ":" + field.getName();
                        updatecols = field.getName() + "=:" + field.getName();
                    } else {
                        fieldname += "," + field.getName();
                        values += ",:" + field.getName();
                        updatecols += "," + field.getName() + "=:" + field.getName();
                    }

                    //return selected fields
                    setvalues += "$values[\":" + field.getName() + "\"] = $obj->" + field.getName() + ";\n\t";

                    updatevalues += "$values[\":" + field.getName() + "\"] = $obj->" + field.getName() + ";\n\t";

                    counter++;
                }

                if (selectcounter == 0) {
                    selectarray = "'" + field.getName() + "' => $row->" + field.getName() + "\n\t\t";
                } else {
                    selectarray += ",'" + field.getName() + "' => $row->" + field.getName() + "\n\t\t";
                }

                selectcounter++;
            }

            selectcounter = 0;
            counter = 0;

            String filecontents = FileHandler.openFile("model_sample.template");
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

        }
    }

    public static void createControllers() {
        ArrayList<MySqlTable> tables = MysqlHandler.getAllTables();

        for (MySqlTable table : tables) {

            String filecontents = FileHandler.openFile("controller.template");
            System.out.println("for " + table.getName());

            filecontents = filecontents.replace("{tablename}", table.getName());
            filecontents = filecontents.replace("{Tablename}", table.getName().substring(0, 1).toUpperCase() + table.getName().substring(1));

            System.out.println(filecontents);
            System.out.println("-----------------------");
            FileHandler.createFile("C:\\wamp64\\www\\testphp\\" + table.getName() + "\\", "controller", filecontents);

        }
    }

    public static void createViews(JTable tblfields, String table) {

        String name = "";
        String datatype = "";
        String htmltype = "";
        String fields = "";
        for (int i = 0; i < tblfields.getRowCount(); i++) {  // Loop through the rows

            name = tblfields.getValueAt(i, 0).toString();
            datatype = tblfields.getValueAt(i, 1).toString();
            htmltype = tblfields.getValueAt(i, 2).toString();
            System.out.println(name + " " + datatype + " " + htmltype);
            
            switch (htmltype) {
                case "textbox":
                    fields += "<label for=\""+name+"\">"+name+":</label><br>\n" +
                                "<input type=\"text\" id=\""+name+"\" name=\""+name+"\" value=\"\"><br>";
                break;
                case "password":
                    fields += "<label for=\""+name+"\">"+name+":</label><br>\n" +
                                "<input type=\"password\" id=\""+name+"\" name=\""+name+"\" value=\"\"><br>";
                break;
                case "textarea":
                    fields += "<label for=\""+name+"\">"+name+":</label><br>\n" +
                                "<textarea id=\""+name+"\" name=\""+name+"\"></textarea><br>";
                break;
                case "date":
                    fields += "<label for=\""+name+"\">"+name+":</label><br>\n" +
                                "<input type=\"date\" id=\""+name+"\" name=\""+name+"\" value=\"\"><br>";
                break;
                case "image":
                    fields += "<label for=\""+name+"\">"+name+":</label><br>\n" +
                                "<img src=\"\" id=\""+name+"\" name=\""+name+"\" alt=\"\"><br>";
                break;
                case "label":
                    fields += "<label for=\""+name+"\">"+name+":</label><br>\n";
                break;
                case "checkbox":
                    fields +="<label for=\""+name+"\"> </label>\n"+
                             "<input type=\"checkbox\" id=\""+name+"\" name=\""+name+"\" value=\"\"><br>";
                break;                
                case "select":
                    fields +="<label for=\""+name+"\"> </label>\n"+
                            "<select name=\""+name+"\" id=\""+name+"\">\n" +
                            "  <option value=\"1\">val1</option>\n" +
                            "  <option value=\"2\">val2</option>\n" +
                            "  <option value=\"3\">val3</option>\n" +
                            "  <option value=\"4\">val4</option>\n" +
                            "</select><br>";
                break;
                case "multiselect":
                    fields +="<label for=\""+name+"\"> </label>\n"+
                            "<select name=\""+name+"\" id=\""+name+"\" size=\"4\" multiple>\n" +
                            "  <option value=\"1\">val1</option>\n" +
                            "  <option value=\"2\">val2</option>\n" +
                            "  <option value=\"3\">val3</option>\n" +
                            "  <option value=\"4\">val4</option>\n" +
                            "</select>";
                break;                
                default:
                // code block
            }

        }

        String filecontents = FileHandler.openFile("form.template");
//            System.out.println("for " + table);
//            filecontents = filecontents.replace("{entity}", table.getName().substring(0, 1).toUpperCase() + table.getName().substring(1));
               filecontents = filecontents.replace("{fields}", fields);
//
            System.out.println(filecontents);
//            System.out.println(table);
//            System.out.println("-----------------------");
            FileHandler.createFile("C:\\wamp64\\www\\testphp\\"+table+"\\", table, filecontents);
            
            

    }
}
