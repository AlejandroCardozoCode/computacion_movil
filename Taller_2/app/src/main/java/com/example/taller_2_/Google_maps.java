package com.example.taller_2_;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.taller_2_.databinding.ActivityGoogleMapsBinding;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.SphericalUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Google_maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityGoogleMapsBinding binding;
    private EditText locationTextv;
    private Marker marcador;
    private Marker marcadorLong;
    String permission = Manifest.permission.ACCESS_FINE_LOCATION;
    int permission_id = 2;
    LatLng actual, marcada;


    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    //sensors
    SensorManager sensorManager;
    Sensor lightSensor;
    SensorEventListener lightSensorListener;
    static final int REQUTEST_CHECK_SETTINGS = 3;
    boolean is_gps_enabled = false;


    //map Search
    Geocoder mGeocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGoogleMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mGeocoder = new Geocoder(this);
        //sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightSensorListener = lightSensorCode();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //inflate
        locationTextv = findViewById(R.id.searchT);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = createLocationRequest();
        locationCallback = createLocationCallback();

        requestPermission(Google_maps.this, permission, "permiso para acceder al gps", permission_id);
        //busqueda
        locationTextv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String address = locationTextv.getText().toString();
                    LatLng position = searchByName(address);
                    marcada = position;
                    if (position != null && mMap != null) {
                        if (marcador != null) {
                            marcador.remove();
                        }
                        marcador = mMap.addMarker(new MarkerOptions().position(position).title(address).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));

                        Double distance = SphericalUtil.computeDistanceBetween(actual, marcada);
                        String dist = String.format("%.2f", distance / 1000);
                        Toast.makeText(Google_maps.this, "Distancia: " + dist + " km", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });
        mapFragment.getMapAsync(this);
    }

    private SensorEventListener lightSensorCode() {
        SensorEventListener sel = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (mMap != null) {
                    if (event.values[0] < 5000) {
                        Log.i("MAPS", "DARK_MAP" + event.values[0]);
                        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(Google_maps.this, R.raw.dark_map));
                    } else {
                        Log.i("MAPS", "WHITE_MAP" + event.values[0]);
                        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(Google_maps.this, R.raw.white_map));
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        return sel;
    }

    private void localizacionActual() {
        if ((ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) && is_gps_enabled) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    actual = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(actual).title("posicion actual"));
                    ;
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(actual));
                }
            });

        }
    }

    private LocationRequest createLocationRequest() {
        LocationRequest locationRequestV = LocationRequest.create()
                .setInterval(10000)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequestV;
    }

    private LocationCallback createLocationCallback() {
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    Log.i("tag", "location: " + location.toString());
                }
            }
        };
        return locationCallback;
    }

    private void requestPermission(Activity context, String permission, String justification, int idCode) {

        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                Toast.makeText(context, justification, Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(context, new String[]{permission}, idCode);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkSettingsLocation();
        sensorManager.registerListener(this.lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
        sensorManager.unregisterListener(this.lightSensorListener);
    }

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            if (is_gps_enabled) {
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
            }
        }
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void checkSettingsLocation() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                is_gps_enabled = true;
                startLocationUpdates();
                localizacionActual();
            }
        });
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                is_gps_enabled = false;
                switch (statusCode) {
                    case CommonStatusCodes
                            .RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(Google_maps.this, REQUTEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {

                        }
                        break;
                    case LocationSettingsStatusCodes
                            .SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUTEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                is_gps_enabled = true;
                startLocationUpdates();
                localizacionActual();
            } else {
                Toast.makeText(Google_maps.this, "El gps no esta activado", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(Google_maps.this, R.raw.white_map));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng latLng) {
                String name = searchByLocation(latLng.latitude, latLng.longitude);
                if(!"".equals(name)){
                    if(marcador != null){
                        marcador.remove();
                    }
                    marcador = mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    marcada = latLng;
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                    Double distance = SphericalUtil.computeDistanceBetween(actual, marcada);
                    String dist = String.format("%.2f", distance / 1000);
                    Toast.makeText(Google_maps.this, "Distancia: " + dist + " km", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private LatLng searchByName(String name) {
        LatLng position = null;
        if (!name.isEmpty()) {
            try {
                List<Address> addresses = mGeocoder.getFromLocationName(name, 2);
                if (addresses != null && !addresses.isEmpty()) {
                    Address addressResult = addresses.get(0);
                    position = new LatLng(addressResult.getLatitude(), addressResult.getLongitude());
                } else {
                    Toast.makeText(Google_maps.this, "Direccion no encontrada", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(Google_maps.this, "la direccion esta vacia", Toast.LENGTH_SHORT).show();
        }
        return position;
    }

    private String searchByLocation(double latitude, double longitude) {
        String addressName = "";
        try {
            List<Address> addresses = mGeocoder.getFromLocation(latitude, longitude, 2);
            if (addresses != null && !addresses.isEmpty()) {
                Address addressResult = addresses.get(0);
                addressName = addressResult.getAddressLine(0);
                Log.i("MapsApp", addressResult.getFeatureName());
            } else {
                Toast.makeText(Google_maps.this, "Direccion no encontrada", Toast.LENGTH_SHORT).show();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressName;
    }
}