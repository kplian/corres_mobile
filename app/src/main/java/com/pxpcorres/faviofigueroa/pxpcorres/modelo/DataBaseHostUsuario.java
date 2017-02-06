package com.pxpcorres.faviofigueroa.pxpcorres.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by lenovo on 21-Jan-16.
 */
public class DataBaseHostUsuario {

    public static final String CREATE_TABLE = "CREATE TABLE hostusuario ("+
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            " usuario TEXT, pwd TEXT, host TEXT,estado TEXT,desc_person TEXT)";

    private DbHelper helper;
    private SQLiteDatabase db;



    public DataBaseHostUsuario(Context context){
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }
    public void cerrarDb(){
        db.close();
    }
    public void abriDb(){
        db.isOpen();
    }

    public ContentValues generarContentValues(String usuario, String pwd , String host,String estado,String desc_person){
        ContentValues valores = new ContentValues();
        valores.put("usuario",usuario);
        valores.put("pwd",pwd);
        valores.put("host",host);
        valores.put("estado",estado);
        valores.put("desc_person",desc_person);
        return valores;
    }

    public void insertar(String usuario, String pwd , String host,String estado,String desc_person){
        db.insert( "hostusuario",null,generarContentValues(usuario,pwd,host,estado,desc_person) );
        db.close();
    }

    public Cursor listarHostUsuario(){
        String[] columnas = new String []{"usuario","pwd","host","estado","desc_person"};
        return  db.query("hostusuario",columnas,null,null,null,null,"_id DESC","1");
    }




}