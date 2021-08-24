package com.example.taller_1;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Paises extends AppCompatActivity {

    ArrayList<Pais> arrelgoPaises = new ArrayList<Pais>();
    ListView listaPaises;

    //inflate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paises);
        //inflate
        listaPaises = findViewById(R.id.listaPantalla);

        //lectura del archivo json
        JSONArray paises = null;
        try {
            JSONObject json = new JSONObject(cargarJson());
            paises = json.getJSONArray("paises");
            for (int i = 0; i < paises.length(); i++) {
                JSONObject jsonObject = paises.getJSONObject(i);
                String vNombrePais = jsonObject.getString("nombre_pais");
                String vCapital = jsonObject.getString("capital");
                String vint = jsonObject.getString("nombre_pais_int");
                String vCodigo = jsonObject.getString("sigla");

                Pais paisActual = new Pais();
                paisActual.setNobmrePais(vNombrePais);
                paisActual.setCapital(vCapital);
                paisActual.setSigla(vCodigo);
                paisActual.setNombreInt(vint);
                arrelgoPaises.add(paisActual);
            }
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ArrayAdapter<Pais> adapter = new ArrayAdapter<Pais>(this, android.R.layout.simple_list_item_1,arrelgoPaises);
        listaPaises.setAdapter(adapter);
        listaPaises.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), InfoPais.class);
                Pais paisSeleccionActual = (Pais) adapter.getItem(position);
                intent.putExtra("nombre",paisSeleccionActual.getNobmrePais());
                intent.putExtra("nombreInt",paisSeleccionActual.getNombreInt());
                intent.putExtra("capital",paisSeleccionActual.getCapital());
                intent.putExtra("sigla",paisSeleccionActual.getSigla());
                startActivity(intent);

            }
        }));

    }
    public String cargarJson() throws UnsupportedEncodingException {
        String json = null;
        try {
            InputStream is = this.getAssets().open("paises.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return json;
        }
        return json;
    }
}