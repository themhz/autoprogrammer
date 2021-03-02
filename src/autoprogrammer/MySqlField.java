package autoprogrammer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author themhz
 */
public class MySqlField {
    private String name;
    private String datatype;  
    private String size;
    private String foreintable;
    private String isprimary;
    private String foreinkey;
    private String isnullable;

    public MySqlField(String name, String datatype, String size, String foreintable, String isprimary, String foreinkey, String isnullable) {
        this.name = name;
        this.datatype = datatype;
        this.size = size;
        this.foreintable = foreintable;
        this.isprimary = isprimary;
        this.foreinkey = foreinkey;
        this.isnullable = isnullable;
    }

    public String getForeinkey() {
        return foreinkey;
    }

    public void setForeinkey(String foreinkey) {
        this.foreinkey = foreinkey;
    }

    public String getIsnullable() {
        return isnullable;
    }

    public void setIsnullable(String isnullable) {
        this.isnullable = isnullable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getForeintable() {
        return foreintable;
    }

    public void setForeintable(String foreintable) {
        this.foreintable = foreintable;
    }

    public String getIsprimary() {
        return isprimary;
    }

    public void setIsprimary(String isprimary) {
        this.isprimary = isprimary;
    }

    public String Isforeinkey() {
        return foreinkey;
    }

    public void setIsforeinkey(String isforeinkey) {
        this.foreinkey = isforeinkey;
    }
    
    
}
