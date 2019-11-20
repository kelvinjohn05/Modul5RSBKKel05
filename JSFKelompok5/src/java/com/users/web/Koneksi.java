package com.users.web;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author RYH
 */
public class Koneksi {
    public static void main(String[] args){
    Koneksi obj_koneksi = new Koneksi();
    System.out.println(obj_koneksi.get_connection());
}
    
public Connection get_connection(){
   Connection connection = null;
   
   try {
       Class.forName("com.mysql.cj.jdbc.Driver");
       connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/kel05", "kel05", "kel05");
   } catch (Exception e) {
       System.out.println(e);
   }
   return connection;
   }
}
