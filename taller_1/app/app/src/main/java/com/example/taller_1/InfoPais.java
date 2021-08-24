package com.example.taller_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebBackForwardList;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class InfoPais extends AppCompatActivity {

    TextView nombrePais, nombreInt, sigla, capital;
    WebView imagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pais);
        //inflate
        nombrePais = findViewById(R.id.paisLabel);
        nombreInt = findViewById(R.id.nombreIntLabel);
        capital = findViewById(R.id.capitalLabel);
        sigla = findViewById(R.id.siglaLabel);
        imagen =  (WebView)findViewById(R.id.imagenWeb);

        //asignacion de los valores
        String bandera = "https://www.banderas-mundo.es/data/flags/w580/";
        String valorNombrePais = getIntent().getStringExtra("nombre");
        String valorInt = getIntent().getStringExtra("nombreInt");
        String valorCapital = getIntent().getStringExtra("capital");
        String valorSigla = getIntent().getStringExtra("sigla");
        String siglaAux = valorSigla;

        //urlBandera
        siglaAux = siglaAux.toLowerCase();
        bandera = bandera + siglaAux +".png";

        nombrePais.setText("Nombre Pais: " + valorNombrePais);
        nombreInt.setText("Nombre Internacional: " + valorInt);
        capital.setText("Capital: " + valorCapital);
        sigla.setText("Sigla: " + valorSigla);
        imagen.setWebViewClient(new WebViewClient());
        imagen.setInitialScale((int) (120 * imagen.getScaleY()));
        imagen.loadUrl(bandera);

    }
}