package com.example.taller_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Fibonacci extends AppCompatActivity {

    TextView textoFibonacci;
    ImageButton fibonnacciBoton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fibonacci);
        //inflate
        textoFibonacci = findViewById(R.id.textoFiboPantalla);
        fibonnacciBoton = findViewById(R.id.imagenBotonFibo);
        //valores en pantalla
        String valorSpinner = getIntent().getStringExtra("valorNumero");
        int valorNumericoSpinner = Integer.parseInt(valorSpinner);
        String cadenaDeValores = "";

        for(int i = 1; i<=valorNumericoSpinner;i++)
        {
            cadenaDeValores = cadenaDeValores + String.valueOf(i) + "\n";
        }
        textoFibonacci.setText(cadenaDeValores);

        fibonnacciBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Web.class);
                startActivity(intent);
            }
        });
    }
}