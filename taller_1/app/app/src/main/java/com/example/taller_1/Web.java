package com.example.taller_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web extends AppCompatActivity {

    WebView pantallaWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        //inflate
        pantallaWeb = (WebView)findViewById(R.id.webPantalla);
        pantallaWeb.setWebViewClient(new WebViewClient());
        pantallaWeb.loadUrl("https://en.wikipedia.org/wiki/Fibonacci");


    }
}