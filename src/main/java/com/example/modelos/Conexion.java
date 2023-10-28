package com.example.modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Conexion {
    private static String server = "localhost";
    private static String user = "topicos";
    private static String pwd = "1234567890";
    private static String db = "restaurante";
    public static Connection conexion;
    public static void createConnection(){
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mariadb://"+server+":3306/"+db,user,pwd);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
