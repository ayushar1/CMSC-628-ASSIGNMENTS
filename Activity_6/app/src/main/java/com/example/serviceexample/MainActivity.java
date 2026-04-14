package com.example.serviceexample;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnStartService;
    private Button btnStopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartService = findViewById(R.id.btnStartService);
        btnStopService  = findViewById(R.id.btnStopService);

        // Step 3 - Start service on button click
        btnStartService.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyService.class);
            startService(intent);
        });

        // Step 3 - Stop service on button click
        btnStopService.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyService.class);
            stopService(intent);
        });
    }
}