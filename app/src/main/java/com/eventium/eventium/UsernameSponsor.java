package com.eventium.eventium;

public class UsernameSponsor {
    private String username;
    private Boolean sponsor;

    public UsernameSponsor(String username, Boolean sponsor) {
        this.username = username;
        this.sponsor = sponsor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getSponsor() {return sponsor;}

    public void setSponsor(Boolean sponsor) {this.sponsor = sponsor;}

}
