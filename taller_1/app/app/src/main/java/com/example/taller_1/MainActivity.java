package com.example.taller_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button bFibonacci;
    Button bFactorial;
    EditText numeroPantallaFactorial;
    Button bPaises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //desactivar el modo oscuro en la app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //inflate
        bFibonacci = findViewById(R.id.boton_fibonacci);
        bFactorial = findViewById(R.id.boton_factorial);
        numeroPantallaFactorial = findViewById(R.id.numeroInputFactorial);
        bPaises = findViewById(R.id.boton_paises);

        bFibonacci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantallaFibo = new Intent(v.getContext(), Fibonacci.class);
                startActivity(pantallaFibo);
            }
        });

        bFactorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valorFactorial = Integer.parseInt(numeroPantallaFactorial.getText().toString());
                Intent intent = new Intent(v.getContext(), Factorial.class);
                intent.putExtra("numero", valorFactorial);
                startActivity(intent);
            }
        });

        bPaises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Paises.class);
                startActivity(intent);
            }
        });


    }
}