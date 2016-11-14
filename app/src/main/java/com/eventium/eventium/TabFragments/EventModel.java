package com.eventium.eventium.TabFragments;

import android.graphics.Bitmap;
import android.net.Uri;

public class EventModel {

    //Uri imagen;
    Bitmap imagen;
    String titulo;
    String ciudad;
    String fechas;
    String horas;
    String precio;

    EventModel(Bitmap imagen, String titulo, String ciudad, String fechas, String horas, String precio) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.ciudad = ciudad;
        this.fechas = fechas;
        this.horas = horas;
        this.precio = precio;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getFechas() {
        return fechas;
    }

    public String getHoras() {
        return horas;
    }

    public String getPrecio() {
        return precio;
    }

}
