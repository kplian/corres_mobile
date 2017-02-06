package com.pxpcorres.faviofigueroa.pxpcorres.control;

/**
 * Created by faviofigueroa on 5/22/16.
 */
public class correspondencia {

    Integer id_correspondencia;
    String numero;
    String documento;
    String remitente;
    String cargo_remitente;

    String estado;

    public correspondencia(Integer id_correspondencia, String numero, String documento, String remitente, String cargo_remitente, String estado) {
        this.id_correspondencia = id_correspondencia;
        this.numero = numero;
        this.documento = documento;
        this.remitente = remitente;
        this.cargo_remitente = cargo_remitente;
        this.estado = estado;
    }

    public Integer getId_correspondencia() {
        return id_correspondencia;
    }

    public void setId_correspondencia(Integer id_correspondencia) {
        this.id_correspondencia = id_correspondencia;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getCargo_remitente() {
        return cargo_remitente;
    }

    public void setCargo_remitente(String cargo_remitente) {
        this.cargo_remitente = cargo_remitente;
    }



    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
