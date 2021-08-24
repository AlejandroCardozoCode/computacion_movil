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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button bFibonacci;
    Button bFactorial;
    EditText numeroPantallaFactorial;
    Button bPaises;
    Spinner pSpinner;
    //contador fibonacci y factorial
    int contadorFibonacci = 0;
    int contadorFactorial = 0;
    String horaUltimo = "";

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
                contadorFibonacci +=1;
                Date horaActual = new Date();
                SimpleDateFormat horaFormato = new SimpleDateFormat("hh:mm:ss");
                horaUltimo = horaFormato.format(horaActual);
            }
        });

        bFactorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valorFactorial = Integer.parseInt(numeroPantallaFactorial.getText().toString());
                Intent intent = new Intent(v.getContext(), Factorial.class);
                intent.putExtra("numero", valorFactorial);
                startActivity(intent);
                contadorFactorial +=1;
                Date horaActual = new Date();
                SimpleDateFormat horaFormato = new SimpleDateFormat("hh:mm:ss");
                horaUltimo = horaFormato.format(horaActual);
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

    @Override
    protected void onResume() {
        super.onResume();
        String cadenaToast = "Fibonacci: " + String.valueOf(contadorFibonacci) + "\nFactorial: " + String.valueOf(contadorFactorial) + "\n" +horaUltimo;
        Toast.makeText(this,cadenaToast , Toast.LENGTH_SHORT).show();
    }
}