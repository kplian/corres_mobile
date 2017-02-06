package com.pxpcorres.faviofigueroa.pxpcorres.control;

/**
 * Created by faviofigueroa on 26/5/16.
 */
public class ControlCorrespondencia {



    Integer id_correspondencia;
    String numero;
    String documento;
    String remitente;
    String cargo_remitente;

    String estado;



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


}
