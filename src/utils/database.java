/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Zarrouk
 */
public class database {
     String url = "jdbc:mysql://localhost:3306/diabety";
     String login = "root";
     String pwd = "";
    public  static database db;
    public Connection con;
    private database() {
        System.out.println("hello");
         try {
             con=DriverManager.getConnection(url, login, pwd);
             System.out.println("connexion etablie");
         } catch (SQLException ex) {
             System.out.println(ex);
         }
    }
    
    public Connection  getConnection()
    {
    return con;
    }     
    public static database getInstance()
    {if(db==null)
        db=new database();
    return db;
    }       
     
}
