package com.eventium.eventium;

public class Evento {
    private String title;
    private String id;
    private String organizerId;
    private String ciudad;
    private String pic;
    private String precio;
    private String fecha_ini;
    private String fecha_fin;
    private String hora_ini;
    private String hora_fin;
    private String categoria;
    private String descripcion;
    private String direccion;

    public Evento(String title, String id, String organizerId, String ciudad, String pic, String precio,
        String fecha_ini, String fecha_fin, String hora_ini, String hora_fin, String categoria, String descripcion, String direccion) {
            this.title = title;
            this.id = id;
            this.organizerId = organizerId;
            this.setCiudad(ciudad);
            this.setPic(pic);
            this.setPrecio(precio);
            this.setFecha_ini(fecha_ini);
            this.setFecha_fin(fecha_fin);
            this.setHora_ini(hora_ini);
            this.setHora_fin(hora_fin);
            this.setCategoria(categoria);
            this.setDescripcion(descripcion);
            this.setDireccion(direccion);
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getId() {
        return id;
    }

    public void setId(String id) {this.id = id;}

    public String getOrganizerId() {return organizerId;}

    public void setOrganizerId(String organizerId) {this.organizerId = organizerId;}

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getFecha_ini() {
        return fecha_ini;
    }

    public void setFecha_ini(String fecha_ini) {
        this.fecha_ini = fecha_ini;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getHora_ini() {
        return hora_ini;
    }

    public void setHora_ini(String hora_ini) {
        this.hora_ini = hora_ini;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    public String getCategoria() { return categoria; }

    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getDireccion() { return direccion; }

    public void setDireccion(String direccion) { this.direccion = direccion; }
}