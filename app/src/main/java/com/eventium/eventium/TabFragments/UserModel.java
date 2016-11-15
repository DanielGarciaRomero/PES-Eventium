package com.eventium.eventium.TabFragments;

import android.graphics.Bitmap;
import android.net.Uri;

public class UserModel {
/*
    Uri imagen;
    String username;

    public UserModel(Uri imagen, String username) {
        this.imagen = imagen;
        this.username = username;
    }

    public Uri getImagen() { return imagen; }

    public String getUsername() { return username; }
    */

    Bitmap imagen;
    String username;

    public UserModel(Bitmap imagen, String username) {
        this.imagen = imagen;
        this.username = username;
    }

    public Bitmap getImagen() { return imagen; }

    public String getUsername() { return username; }

}