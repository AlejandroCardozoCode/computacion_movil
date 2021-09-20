package com.example.taller_2_;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Camara extends AppCompatActivity {
    ImageView imageITF;
    Button btGalerry;
    Button btCamera;
    //permisos
    String cameraPermission = Manifest.permission.CAMERA;
    String galleryPermission = Manifest.permission.READ_EXTERNAL_STORAGE;
    int idCameraPermission = 0;
    int idGalleryPermission = 1;
    public static final int CAMERA_ID = 0;
    public static final int GALLERY_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        //inflate
        imageITF = findViewById(R.id.imageITF);
        btGalerry = findViewById(R.id.btSeleccionarImagen);
        btCamera = findViewById(R.id.btCamara);

        btGalerry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(view.getContext(),galleryPermission) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission((Activity) view.getContext(), galleryPermission, "permiso para acceder a la galeria", idGalleryPermission);
                }
                else{
                    gallerySelector();
                }
            }
        });

        btCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(view.getContext(),cameraPermission) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission((Activity) view.getContext(), cameraPermission, "permiso para acceder a la camara", idCameraPermission);
                }
                else{
                    takePicture();
                }
            }
        });
    }

    private void gallerySelector(){
        if (ActivityCompat.checkSelfPermission(this,galleryPermission) == PackageManager.PERMISSION_GRANTED) {

            Intent pickImage = new Intent(Intent.ACTION_PICK);
            pickImage.setType("image/*");
            startActivityForResult(pickImage, GALLERY_ID);
        }
    }

    private void takePicture(){
        if (ActivityCompat.checkSelfPermission(this,cameraPermission)== PackageManager.PERMISSION_GRANTED) {
            Log.i("PERMISSION_APP", "Inside camera method");
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(takePictureIntent,CAMERA_ID);
            }catch (ActivityNotFoundException e)
            {
                Log.e("PERMISSION_APP", e.getMessage());
            }
        }
    }

    private void requestPermission(Activity context, String permission, String justification, int idCode){

        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(context,permission)){
                Toast.makeText(context, justification, Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(context, new String[]{permission},idCode);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]grantResults)
    {
        if(requestCode == idCameraPermission){
            takePicture();
        }
        else if (requestCode == idGalleryPermission){
            gallerySelector();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){

            case GALLERY_ID:
                if (resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageITF.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                };
                break;
            case CAMERA_ID:
                if (resultCode == RESULT_OK){
                    Bundle extras = data.getExtras();
                    Bitmap imageBitMap = (Bitmap) extras.get("data");
                    imageITF.setImageBitmap(imageBitMap);
                }
        }
    }
}