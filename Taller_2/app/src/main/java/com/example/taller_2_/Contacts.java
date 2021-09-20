package com.example.taller_2_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;
import android.widget.Toast;

import com.example.taller_2_.adapters.Contacts_adapters;

public class Contacts extends AppCompatActivity {

    ListView list_contacts;

    String contact_permission = Manifest.permission.READ_CONTACTS;
    public static final int CONTACTS_ID = 2;
    Contacts_adapters adapter;
    String[] projection = new String[]{ContactsContract.Profile._ID,ContactsContract.Profile.DISPLAY_NAME};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        //inflate
        list_contacts  = findViewById(R.id.list_contacts);
        //adapter
        adapter = new Contacts_adapters(this,null,0);
        list_contacts.setAdapter(adapter);
        requestPermission(this,contact_permission,"Permiso para mostrar contactos",CONTACTS_ID);
        update();
    }

    private void update()
    {
        if(ContextCompat.checkSelfPermission(this,contact_permission) == PackageManager.PERMISSION_GRANTED)
        {
            Cursor contacts_cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,projection,null,null,null);
            adapter.changeCursor(contacts_cursor);

        }else {
            Toast.makeText(this,"No se tiene permisos para mostrar contactos", Toast.LENGTH_LONG);
        }
    }
    private void requestPermission(Activity context, String permiso, String justification, int id)
    {
        if(ContextCompat.checkSelfPermission(context,contact_permission) == PackageManager.PERMISSION_DENIED)
        {
            Toast.makeText(context,justification,Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(context, new String[]{contact_permission},id);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CONTACTS_ID)
        {
            update();
        }
    }
}