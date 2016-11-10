package com.eventium.eventium;

public class Evento {
    private String title;
    private String id;
    private String organizerId;

    public Evento(String title, String id, String organizerId) {
        this.title = title;
        this.id = id;
        this.organizerId = organizerId;
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getId() {
        return id;
    }

    public void setId(String id) {this.id = id;}

    public String getOrganizerId() {return organizerId;}

    public void setOrganizerId(String organizerId) {this.organizerId = organizerId;}
}