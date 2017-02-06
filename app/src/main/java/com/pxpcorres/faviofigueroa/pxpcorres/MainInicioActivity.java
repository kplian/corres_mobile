package com.pxpcorres.faviofigueroa.pxpcorres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainInicioActivity extends AppCompatActivity {


    Button buttonContinuar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main_inicio);


        buttonContinuar = (Button) findViewById(R.id.buttonContinuarToLogin);

        buttonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLogin();

            }
        });




    }

    private void startLogin(){
        Intent intent = new Intent(this,MainActivity.class);

        startActivity(intent);

    }
}
