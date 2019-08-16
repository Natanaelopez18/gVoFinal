package com.example.elvoluntariado.models;

import java.util.Date;

public class Proyectos {

    private String name;
    private String descrip;
    private Date fechaInicio;
    private Date fechaFin;
    private int cupos;
    private int idFoundationP;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getCupos() {
        return cupos;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
    }

    public int getIdFoundationP() {
        return idFoundationP;
    }

    public void setIdFoundationP(int idFoundationP) {
        this.idFoundationP = idFoundationP;
    }
}
