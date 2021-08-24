package com.example.taller_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Fibonacci extends AppCompatActivity {

    TextView textoFibonacci;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fibonacci);
        //inflate
        textoFibonacci = findViewById(R.id.textoFiboPantalla);
        String valorSpinner = getIntent().getStringExtra("valorNumero");
        int valorNumericoSpinner = Integer.parseInt(valorSpinner);
        String cadenaDeValores = "";

        for(int i = 1; i<=valorNumericoSpinner;i++)
        {
            cadenaDeValores = cadenaDeValores + String.valueOf(i) + "\n";
        }
        textoFibonacci.setText(cadenaDeValores);
    }
}