package com.pxpcorres.faviofigueroa.pxpcorres.pxpservice_android;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.spongycastle.crypto.InvalidCipherTextException;



import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by favio figueroa on 27/12/2015.
 */
public class PxpService {

    private String usuario; //usuario del pxp
    private String pwd; //pasword del pxp
    private String url; //url del servicio
    private String host; //host
    private String sistema;
    private String clase;
    private String metodo;
    private String postBody;

    private String mensaje;

    private boolean md5_service;

    private RequestBody parametros;
    PxpEncryption pxpEncryption = new PxpEncryption();




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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public RequestBody getParametros() {
        return parametros;
    }




    public void setParametros(RequestBody parametros) {
        this.parametros = parametros;
    }





    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }



    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }




    public void dominioDeServicio(String host,String sistema,String clase,String metodo){

        //http:/10.0.0.28/kerp/pxp/lib/rest/seguridad/Persona/listarPersonaFoto
        this.url = "http:/"+host+"/pxp/lib/rest/"+sistema+"/"+clase+"/"+metodo+"";
        //Log.d("url",this.url);

    }
    public void enviarParametros(){

        this.postBody = "start=0&limit=5&sort=id_persona&dir=asc";

    }

    public Call servicio() throws InvalidCipherTextException {

        Log.d("md5 encriptacion estado", String.valueOf(isMd5_service()));


        pxpEncryption.md5_encryp=isMd5_service(); //le digo si hara md5 o no

        String llave = pxpEncryption.PXPEncryptRijndael256(this.getUsuario(), this.getPwd());
        Log.d("rijndael 256 con base64", llave);

        this.setPwd(llave);

        OkHttpClient client = new OkHttpClient();




        Request request = new Request.Builder()
                .url(this.url)
                .post(this.parametros)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Php-Auth-User", this.pwd)
                .addHeader("Pxp-user", this.usuario)
                .build();

        //Log.e(TAG,request.toString());
        Call call = client.newCall(request);
        return call;

    }

    //verificamos si se tiene conexion
    public boolean isNetworkAvailable(Context context){

        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;

    }

    public String ExisteError(String JsonData) throws JSONException {

        JSONObject json_pxp = new JSONObject(JsonData);
        String root = json_pxp.getString("ROOT");
        JSONObject json_root = new JSONObject(root);
       // JSONArray respuesta = json_pxp.getJSONArray("ROOT");

        String detalle = json_root.getString("detalle");
        JSONObject json_detalle = new JSONObject(detalle);

        String mensaje = json_detalle.getString("mensaje");

       // Toast.makeText(context,mensaje,Toast.LENGTH_LONG).show();

        return mensaje;
    }

    public Call iniciar(Context context,boolean md5) throws InvalidCipherTextException {

        setMd5_service(md5);

        Call call = null;
        if(isNetworkAvailable(context)){
            Log.d("internet","si se tiene");
            Toast.makeText(context,"Conectando al Servicio",Toast.LENGTH_LONG).show();
            call = servicio();

        }else {

            Log.d("internet","no se tiene");
            Toast.makeText(context,"no se tiene conexion verifique su conexion a internet",Toast.LENGTH_LONG).show();

        }



        return call;
    }


    public boolean isMd5_service() {
        return md5_service;
    }

    public void setMd5_service(boolean md5_service) {
        this.md5_service = md5_service;
    }
}