package com.eventium.eventium;

public class Comentario {
    private Integer eventid;
    private Integer userid;
    private String text;

    public Comentario (Integer eventid, String text, Integer userid){
        this.eventid = eventid;
        this.text = text;
        this.userid = userid;
    }

    public Integer getEventid() {
        return eventid;
    }

    public void setEventid(Integer eventid) {
        this.eventid = eventid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
