package com.example.taller_2_;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.taller_2_.databinding.ActivityGoogleMapsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Google_maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityGoogleMapsBinding binding;

    String permission = Manifest.permission.ACCESS_FINE_LOCATION;
    int permission_id = 2;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    static final int REQUTEST_CHECK_SETTINGS = 3;
    boolean is_gps_enabled = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = createLocationRequest();
        locationCallback = createLocationCallback();

        requestPermission(this,permission,"permiso para acceder al gps", permission_id);

        binding = ActivityGoogleMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    private LocationRequest createLocationRequest()
    {
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(10000)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }
    private LocationCallback createLocationCallback() {
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                if(location != null){
                    Log.i("tag","location: "+ location.toString());
                }
            }
        };
        return locationCallback;
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
    protected void onResume(){
        super.onResume();
        checkSettingsLocation();
    }
    @Override
    protected void onPause(){
        super.onPause();
        stopLocationUpdates();
    }
    private void startLocationUpdates(){
        if (ContextCompat.checkSelfPermission(this,permission) == PackageManager.PERMISSION_GRANTED) {
            if(is_gps_enabled){
                fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, null);
            }
        }
    }

    private  void stopLocationUpdates(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
    private void checkSettingsLocation(){
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                is_gps_enabled = true;
                startLocationUpdates();
            }
        });
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode= ((ApiException) e).getStatusCode();
                is_gps_enabled = false;
                switch (statusCode){
                    case CommonStatusCodes
                            .RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(Google_maps.this, REQUTEST_CHECK_SETTINGS);
                        }catch (IntentSender.SendIntentException sendEx){

                        }break;
                    case LocationSettingsStatusCodes
                            .SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUTEST_CHECK_SETTINGS)
        {
            if(resultCode== RESULT_OK){
                is_gps_enabled = true;
                startLocationUpdates();
            }
            else{
                Toast.makeText(this,"El gps no esta activado", Toast.LENGTH_LONG).show();
            }
        }

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));

        if(ContextCompat.checkSelfPermission(this,permission) == PackageManager.PERMISSION_GRANTED ) {

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    LatLng actual = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(actual).title("posicion actual"));;
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(actual));
                }
            });

        }
    }
}