package com.example.listviewactivityivan;

import java.io.Serializable;

public class Contacte implements Serializable {
    private static int contador = 0;

    private int id;
    private String nom;
    private String cognoms;
    private String adreca;
    private String telefon;
    private String email;
    private String dataNaixement;
    //nuevo
    public Contacte(String nom, String cognoms, String adreca, String telefon, String email, String dataNaixement) {
        this(++contador, nom, cognoms, adreca, telefon, email, dataNaixement);
    }
    //editar
    public Contacte(int id, String nom, String cognoms, String adreca, String telefon, String email, String dataNaixement) {
        this.id = id;
        this.nom = nom;
        this.cognoms = cognoms;
        this.adreca = adreca;
        this.telefon = telefon;
        this.email = email;
        this.dataNaixement = dataNaixement;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getCognoms() { return cognoms; }
    public void setCognoms(String cognoms) { this.cognoms = cognoms; }
    public String getAdreca() { return adreca; }
    public void setAdreca(String adreca) { this.adreca = adreca; }
    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDataNaixement() { return dataNaixement; }
    public void setDataNaixement(String dataNaixement) { this.dataNaixement = dataNaixement; }


    public String getNomComplet() {
        return (nom + " " + cognoms);
    }
}
