/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author themhz
 */
public class FileHandler {
    public static String openFile(String filepath){
        String contents = "";
        try {
//constructor of the File class having file as an argument  
            FileReader fr = new FileReader(filepath);
            System.out.println("file content: ");
            int r = 0;            
            while ((r = fr.read()) != -1) {
                //System.out.print((char) r);  //prints the content of the file
                contents += (char) r;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return contents;
    }
    
    public static void createFile(String filepath, String contents){
        try {              
            Writer w = new FileWriter(filepath+".php");  
            String content = contents;
            w.write(content);  
            w.close();  
            System.out.println("Done");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
    
    public static void createFile(String filepath, String filename, String contents){
        try {  
            File dir = new File(filepath);
            dir.mkdirs();
            Writer w = new FileWriter(filepath+filename+".php");  
            String content = contents;
            w.write(content);  
            w.close();  
            System.out.println("Done");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
}
