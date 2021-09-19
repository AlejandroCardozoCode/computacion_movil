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
        int valorFibo = Integer.parseInt(valorSpinner);
        String cadenaDeValores = "";
        int a = 0, b = 1, c = 0;

        if(valorFibo >=2)
        {
            cadenaDeValores = cadenaDeValores + "\n" + 0 + "\n" + 1;
        }
        else if (valorFibo == 1)
        {
            cadenaDeValores = cadenaDeValores + "\n" + 0 ;
        }
        for(int i = 2; i<valorFibo;i++)
        {
            c = a + b;
            cadenaDeValores = cadenaDeValores + "\n"+ String.valueOf(c) ;
            a = b;
            b = c;
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