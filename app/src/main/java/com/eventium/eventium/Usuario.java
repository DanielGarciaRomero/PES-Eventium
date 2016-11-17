package com.eventium.eventium;

public class Usuario {
    private String username;
    private String saldo;
    private String mail;
    private String password;
    private String pic;
    private String id;

    public Usuario(String username, String saldo, String mail, String password, String pic, String id) {
        this.username = username;
        this.saldo = saldo;
        this.mail = mail;
        this.password = password;
        this.pic = pic;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaldo() {return saldo;}

    public void setSaldo(String saldo) {this.saldo = saldo;}
}
