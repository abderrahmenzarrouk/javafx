/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.question;
import entities.test;
import interfaces.questioninterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.database;

/**
 *
 * @author Zarrouk
 */
public class questioncrud implements questioninterface<question> {
      private Connection con;
    private Statement ste;
    private PreparedStatement pst ;
    private ResultSet res ;
    
    public questioncrud() {
        con = database.getInstance().getConnection();
        
    }

    @Override
    public void add(question t) throws SQLException {
        Statement st = con.createStatement();
          String req =" insert into question (enonce,reponse1,reponse2,reponse_ideal,signification)"+ "values ('"+t.getEnonce()+" ' ,'"+t.getReponse1()+" ' ,'"+t.getReponse2()+" ' , '"+t.getReponse_ideal() + " ' , '"+t.getSignification()+ " ')"; 
    st.executeUpdate(req);
    System.out.println("question ajouté");
    }

    @Override
    public ObservableList<question> read() throws SQLException{
    ObservableList<question> ls = FXCollections.observableArrayList();
    Statement st = con.createStatement();
    String req = "select * from question";
    ResultSet rs = st.executeQuery(req);
      
     
    while(rs.next()){
        int id = rs.getInt("id");
        String enonce = rs.getString("enonce");
        String reponse1 = rs.getString("reponse1");
        String reponse2 = rs.getString("reponse2");
        String reponse_ideal = rs.getString("reponse_ideal");
        String signification = rs.getString("signification");
        

        question q = new question(id, enonce, reponse1, reponse2, reponse_ideal, signification);
        System.out.println(q);
        ls.add(q);

    }
    
    return ls;

    }


    @Override
    public void update(question t) throws SQLException {
        Statement st = con.createStatement();
        String req = "UPDATE question SET signification = '" + t.getSignification()
                +"', reponse1 = '" + t.getReponse1()
                +"', reponse2 = '" + t.getReponse2()
                +"', reponse_ideal = '" + t.getReponse_ideal()
                +"', enonce = '" + t.getEnonce()
                + "' WHERE id = '" + t.getId()+ "'";
        st.executeUpdate(req);
       System.out.println("question modifiée");
    }

    @Override
    public void delete(question t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    public ObservableList<test> readtest() throws SQLException{
    ObservableList<test> ls = FXCollections.observableArrayList();
    Statement st = con.createStatement();
    String req = "select * from test";
    ResultSet rs = st.executeQuery(req);
      
     
    while(rs.next()){
        int id = rs.getInt("id");
        String email = rs.getString("email");
        String date = rs.getString("date");
        Double resultat = rs.getDouble("resultat");
        test t = new test( id,email, resultat,date);
        System.out.println(t);
        ls.add(t);

    }
    
    return ls;

    }
    
    public void addtest(test t) throws SQLException {
        Statement st = con.createStatement();
          String req =" insert into test (date,resultat,email)"+ "values ('"+t.getDate()+"' , '"+t.getResultat()+"', '"+t.getEmail()+"'   )"; 
          
    st.executeUpdate(req);
    System.out.println("test ajouté");
    }
}
