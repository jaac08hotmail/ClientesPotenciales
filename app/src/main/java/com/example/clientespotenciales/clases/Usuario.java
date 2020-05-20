package com.example.clientespotenciales.clases;

import java.util.ArrayList;

public class Usuario {

    private Integer IDUser;
    private String login;
    private String passw;
    private String Unombres;
    private String Uemail;
    private String UFkRol;
    private String Rnombre;
    private  ArrayList<Modulo> modulos;


    public Usuario(){

    }
    public Integer getIDUser() {
        return IDUser;
    }

    public void setIDUser(Integer IDUser) {
        this.IDUser = IDUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public String getUnombres() {
        return Unombres;
    }

    public void setUnombres(String unombres) {
        Unombres = unombres;
    }

    public String getUemail() {
        return Uemail;
    }

    public void setUemail(String uemail) {
        Uemail = uemail;
    }

    public String getUFkRol() {
        return UFkRol;
    }

    public void setUFkRol(String UFkRol) {
        this.UFkRol = UFkRol;
    }

    public String getRnombre() {
        return Rnombre;
    }

    public void setRnombre(String rnombre) {
        Rnombre = rnombre;
    }

    public ArrayList<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(ArrayList<Modulo> modulos) {
        this.modulos = modulos;
    }
}
