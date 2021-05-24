package com.nelson.weatherapp.Utils;

import java.io.Serializable;

public class DataSearch2 implements Serializable {

    private String temperatura;
    private String temperaturamaxima;
    private String temperaturaminima;
    private String fechaactual;
    private String humedad;
    private String visibilidad;
    private String previsibilidad;
    private String climaactual;


    public DataSearch2() {
        this.temperatura = "";
        this.temperaturamaxima = "";
        this.temperaturaminima = "";
        this.fechaactual = "";
        this.humedad = "";
        this.visibilidad = "";
        this.previsibilidad = "";
        this.climaactual = "";
    }


    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getTemperaturamaxima() {
        return temperaturamaxima;
    }

    public void setTemperaturamaxima(String temperaturamaxima) {
        this.temperaturamaxima = temperaturamaxima;
    }

    public String getTemperaturaminima() {
        return temperaturaminima;
    }

    public void setTemperaturaminima(String temperaturaminima) {
        this.temperaturaminima = temperaturaminima;
    }

    public String getFechaactual() {
        return fechaactual;
    }

    public void setFechaactual(String fechaactual) {
        this.fechaactual = fechaactual;
    }

    public String getHumedad() {
        return humedad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public String getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(String visibilidad) {
        this.visibilidad = visibilidad;
    }

    public String getPrevisibilidad() {
        return previsibilidad;
    }

    public void setPrevisibilidad(String previsibilidad) {
        this.previsibilidad = previsibilidad;
    }

    public String getClimaactual() {
        return climaactual;
    }

    public void setClimaactual(String climaactual) {
        this.climaactual = climaactual;
    }

}
