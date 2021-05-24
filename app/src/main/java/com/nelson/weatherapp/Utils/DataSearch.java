package com.nelson.weatherapp.Utils;

import java.io.Serializable;

public class DataSearch implements Serializable {

    private String idciudad;
    private String nombreciudad;
    private String locacion;


    public DataSearch() {
        this.idciudad = "";
        this.nombreciudad = "";
        this.locacion = "";
    }


    public String getIdciudad() {
        return idciudad;
    }

    public void setIdciudad(String idciudad) {
        this.idciudad = idciudad;
    }

    public String getNombreciudad() {
        return nombreciudad;
    }

    public void setNombreciudad(String nombreciudad) {
        this.nombreciudad = nombreciudad;
    }

    public String getLocacion() {
        return locacion;
    }

    public void setLocacion(String locacion) {
        this.locacion = locacion;
    }

}