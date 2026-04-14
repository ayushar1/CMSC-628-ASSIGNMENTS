package com.example.locationapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.app.ActivityCompat;
import androidx.annotation.NonNull;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private FusedLocationProviderClient fusedLocationClient;
    private TextView textViewLat;
    private TextView textViewLon;
    private Button btnGetLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewLat = findViewById(R.id.textViewLat);
        textViewLon = findViewById(R.id.textViewLon);
        btnGetLocation = findViewById(R.id.btnGetLocation);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        btnGetLocation.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        });
    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        com.google.android.gms.location.CurrentLocationRequest request =
                new com.google.android.gms.location.CurrentLocationRequest.Builder()
                        .setPriority(com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY)
                        .setMaxUpdateAgeMillis(0)
                        .build();

        fusedLocationClient.getCurrentLocation(request, null)
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        textViewLat.setText("Latitude: " + location.getLatitude());
                        textViewLon.setText("Longitude: " + location.getLongitude());
                    } else {
                        Toast.makeText(this,
                                "Still unable to get location. Check GPS is enabled.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this,
                        "Location access is required to use this feature.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}