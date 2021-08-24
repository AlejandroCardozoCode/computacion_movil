package com.example.taller_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button bFibonacci;
    Button bFactorial;
    EditText numeroPantallaFactorial;
    Button bPaises;
    Spinner pSpinner;

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
        pSpinner = findViewById(R.id.spinnerPantalla);

        bFibonacci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorSpinner = (String)pSpinner.getSelectedItem();
                Intent pantallaFibo = new Intent(v.getContext(), Fibonacci.class);
                pantallaFibo.putExtra("valorNumero", valorSpinner);
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