package com.pxpcorres.faviofigueroa.pxpcorres.control;

import android.content.Context;
import android.database.Cursor;

import com.pxpcorres.faviofigueroa.pxpcorres.modelo.DataBaseHostUsuario;

/**
 * Created by dyd on 23/01/2016.
 */
public class ControlHostUsuario {

    private String usuario;
    private String pwd;
    private String host;
    private String estado;
    private String desc_person;

    private DataBaseHostUsuario manager;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDesc_person() {
        return desc_person;
    }

    public void setDesc_person(String desc_person) {
        this.desc_person = desc_person;
    }

    public void insertarHostUsuario(Context context){
        manager = new DataBaseHostUsuario(context);
        manager.insertar(this.usuario,this.pwd,this.host,this.estado,this.desc_person);
    }

    public Cursor listarHostUsuario(Context context){
        manager = new DataBaseHostUsuario(context);
        return manager.listarHostUsuario();
    }


}
