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
    String id;

    public EventModel(Bitmap imagen, String titulo, String ciudad, String fechas, String horas, String precio, String id) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.ciudad = ciudad;
        this.fechas = fechas;
        this.horas = horas;
        this.precio = precio;
        this.id = id;
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

    public String getId() { return id;}

}
