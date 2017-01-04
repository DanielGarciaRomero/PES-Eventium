package com.eventium.eventium;

public class Usuario {
    private String username;
    private String saldo;
    private String mail;
    private String password;
    private String pic;
    private String id;
    private Boolean isVerified;
    private Boolean isBanned;
    private String ciudad;
    private Integer nreports;
    private String valoracion;

    public Usuario(String username, String saldo, String mail, String password, String pic, String id,
                   Boolean isVerified, Boolean isBanned, String ciudad, Integer nreports, String valoracion) {
        this.username = username;
        this.saldo = saldo;
        this.mail = mail;
        this.password = password;
        this.pic = pic;
        this.id = id;
        this.isVerified = isVerified;
        this.isBanned = isBanned;
        this.ciudad = ciudad;
        this.nreports = nreports;
        this.valoracion = valoracion;
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

    public Boolean getIsVerified() {return isVerified;}

    public void setIsVerified(Boolean isVerified) {this.isVerified = isVerified;}

    public Boolean getIsBanned() {return isBanned;}

    public void setIsBanned(Boolean isBanned) {this.isBanned = isBanned;}

    public String getCiudad() {return ciudad;}

    public void setCiudad(String ciudad) {this.ciudad = ciudad;}

    public Integer getNreports() {
        return nreports;
    }

    public void setNreports(Integer nreports) {
        this.nreports = nreports;
    }

    public String getValoracion() { return valoracion; }

    public void setValoracion(String valoracion) { this.valoracion = valoracion;}
}
