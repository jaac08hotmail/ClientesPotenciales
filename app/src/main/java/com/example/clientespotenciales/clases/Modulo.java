package com.example.clientespotenciales.clases;

public class Modulo {

    private Integer IDMod;
    private String Mnombre;
    private String MDescrip;
    private String MFkRol;


    public Modulo() {

    }
    public Integer getIDMod() {
        return IDMod;
    }

    public void setIDMod(Integer IDMod) {
        this.IDMod = IDMod;
    }

    public String getMnombre() {
        return Mnombre;
    }

    public void setMnombre(String mnombre) {
        Mnombre = mnombre;
    }

    public String getMDescrip() {
        return MDescrip;
    }

    public void setMDescrip(String MDescrip) {
        this.MDescrip = MDescrip;
    }

    public String getMFkRol() {
        return MFkRol;
    }

    public void setMFkRol(String MFkRol) {
        this.MFkRol = MFkRol;
    }
}
