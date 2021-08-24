package com.example.taller_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Factorial extends AppCompatActivity {

    TextView textoPantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factorial);

        //inflate
        textoPantalla = findViewById(R.id.textoFactorial);
        int numeroFactorial = getIntent().getIntExtra("numero", 0);
        String cadenaMultiplicacion = "Multiplicacion: ";
        cadenaMultiplicacion = cadenaMultiplicacion + String.valueOf(numeroFactorial);
        int aux = numeroFactorial;
        int contador = 1;
        for(int i = numeroFactorial; i > 1;i--)
        {
           aux = aux -1;
           contador = contador * i;
           cadenaMultiplicacion = cadenaMultiplicacion + " * " + String.valueOf(aux);
        }

        cadenaMultiplicacion = cadenaMultiplicacion + "\n" + "Resultado: " + String.valueOf(contador);

        textoPantalla.setText(cadenaMultiplicacion);
    }
}