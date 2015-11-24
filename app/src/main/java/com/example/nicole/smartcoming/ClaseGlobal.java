package com.example.nicole.smartcoming;

import android.app.Application;

/**
 * Created by Nicole on 20-08-2015.
 */
public class ClaseGlobal extends Application {

    private int id_usuario;
    private String urlPhoto;

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }
}
