package com.pxpcorres.faviofigueroa.pxpcorres;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pxpcorres.faviofigueroa.pxpcorres.control.ControlHostUsuario;
import com.pxpcorres.faviofigueroa.pxpcorres.pxpservice_android.PxpEncryption;
import com.pxpcorres.faviofigueroa.pxpcorres.pxpservice_android.PxpService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private PxpService pxpService = new PxpService();
    public Context contexGlobal;

    Button buttonLogin;
    EditText editTextUsuario;
    EditText editTextContrasena;
    EditText editTextHostIp;


    private ControlHostUsuario controlHostUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                buttonLogin.setEnabled(false);

                editTextHostIp = (EditText)findViewById(R.id.editTextHostIp);
                editTextUsuario = (EditText)findViewById(R.id.editTextUsuario);
                editTextContrasena = (EditText)findViewById(R.id.editTextContrasena);


                String host = editTextHostIp.getText().toString();
                String usuario =  editTextUsuario.getText().toString();
                String pwd = editTextContrasena.getText().toString();

                boolean validado=true;
                if(host.equals("")){
                    validado=false;
                    Toast.makeText(contexGlobal,"Host no debe estar vacio", Toast.LENGTH_LONG).show();
                }
                if(usuario.equals("")){
                    validado=false;
                    Toast.makeText(contexGlobal,"usuario no debe estar vacio", Toast.LENGTH_LONG).show();
                }
                if(pwd.equals("")){
                    validado=false;
                    Toast.makeText(contexGlobal,"pasword no debe estar vacio", Toast.LENGTH_LONG).show();
                }


                if(validado){
                    session(host, usuario, pwd);
                    //startMainMenu();
                }




            }


        });

        contexGlobal = this;

    }


    private void startMainMenu(){
        Intent intent = new Intent(this,MainMenu.class);

        startActivity(intent);

    }

    private void session(final String host, final String usuario, final String pwd){

        try {


            pxpService.setUsuario(usuario);
            pxpService.setPwd(pwd);
            pxpService.dominioDeServicio(host, "seguridad", "Auten", "verificarCredenciales");
            RequestBody formBody = new FormBody.Builder()
                    .add("contrasena", "")
                    .add("usuario", "")

                    .build();
            pxpService.setParametros(formBody);

            Call call = pxpService.iniciar(contexGlobal,true);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("error", String.valueOf(call));
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        //Log.e(TAG,response.body().string());
                        String jsonData = response.body().string();
                        //Log.d("json", jsonData);
                        if (response.isSuccessful()) {
                            Log.d("json", jsonData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    buttonLogin.setEnabled(true);
                                }
                            });


                            controlHostUsuario = getCurrentDetails(jsonData,host,usuario,pwd);



                        } else {
                            pxpService.setMensaje(pxpService.ExisteError(jsonData));


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(contexGlobal, pxpService.getMensaje(), Toast.LENGTH_LONG).show();
                                }
                            });

                        }

                    } catch (IOException e) {
                        Log.e("", "ERROR", e);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                private ControlHostUsuario getCurrentDetails(String jsonData,String host,String usuario,String pwd) throws JSONException {


                    JSONObject item = new JSONObject(jsonData);
                    ;
                    Log.d("i", String.valueOf(item));
                    Log.d("nombre", String.valueOf(item.getString("nombre_usuario")));


                    ControlHostUsuario currentHostUsuario = new ControlHostUsuario();

                    PxpEncryption pxpEncryption = new PxpEncryption();


                    currentHostUsuario.setEstado("activo");
                    currentHostUsuario.setHost(host);
                    currentHostUsuario.setUsuario(usuario);
                    currentHostUsuario.setPwd(pxpEncryption.md5(pwd));
                    currentHostUsuario.setDesc_person(item.getString("nombre_usuario"));
                    currentHostUsuario.insertarHostUsuario(contexGlobal);



                    startMainMenu();
                    return new ControlHostUsuario();
                }



            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
