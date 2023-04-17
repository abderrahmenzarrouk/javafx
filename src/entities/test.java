/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author Zarrouk
 */
public class test {
    private Integer id;
    private String email;
    private Double resultat;
    private String date;

    public test() {
    }

    @Override
    public String toString() {
        return "test{" + "date=" + date + '}';
    }

    public test(Double resultat, String date) {
        this.resultat = resultat;
        this.date = date;
    }

    public test(String email, Double resultat, String date) {
        this.email = email;
        this.resultat = resultat;
        this.date = date;
    }

    public test(String date) {
        this.date = date;
    }

    public test(Integer id, String email, Double resultat, String date) {
        this.id = id;
        this.email = email;
        this.resultat = resultat;
        this.date = date;
    }

    public test(String email, String date) {
        this.email = email;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getResultat() {
        return resultat;
    }

    public void setResultat(Double resultat) {
        this.resultat = resultat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
}
