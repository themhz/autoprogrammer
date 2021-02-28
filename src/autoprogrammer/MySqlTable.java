/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoprogrammer;

import java.util.ArrayList;

/**
 *
 * @author themhz
 */
public class MySqlTable {
    private String name;
    private ArrayList<MySqlField> fields;

    public MySqlTable(String name) {
        this.name = name;    
    }
    
    public MySqlTable(String name, ArrayList<MySqlField> fields) {
        this.name = name;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<MySqlField> getFields() {
        return fields;
    }

    public void setFields(ArrayList<MySqlField> fields) {
        this.fields = fields;
    }
    
    
}
