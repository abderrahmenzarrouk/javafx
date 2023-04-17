/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author Zarrouk
 */
public class question {
    private Integer id;
    private String enonce;
    private String reponse1;
    private String reponse2;
    private String reponse_ideal;
    private String signification;

    public question() {
    }
    public question(Integer id, String enonce, String reponse1, String reponse2, String reponse_ideal, String signification) {
        this.id = id;
        this.enonce = enonce;
        this.reponse1 = reponse1;
        this.reponse2 = reponse2;
        this.reponse_ideal = reponse_ideal;
        this.signification = signification;
    }
    public question(String enonce, String reponse1, String reponse2, String reponse_ideal, String signification) {
        this.enonce = enonce;
        this.reponse1 = reponse1;
        this.reponse2 = reponse2;
        this.reponse_ideal = reponse_ideal;
        this.signification = signification;
    }

    public String getEnonce() {
        return enonce;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public String getReponse1() {
        return reponse1;
    }

    public void setReponse1(String reponse1) {
        this.reponse1 = reponse1;
    }

    public String getReponse2() {
        return reponse2;
    }

    public void setReponse2(String reponse2) {
        this.reponse2 = reponse2;
    }

    public String getReponse_ideal() {
        return reponse_ideal;
    }

    public void setReponse_ideal(String reponse_ideal) {
        this.reponse_ideal = reponse_ideal;
    }

    public String getSignification() {
        return signification;
    }

    public void setSignification(String signification) {
        this.signification = signification;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
