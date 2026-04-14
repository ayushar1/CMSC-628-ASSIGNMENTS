package com.ayush.phoneorientationapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;

    private TextView tvAccelX, tvAccelY, tvAccelZ;
    private TextView tvGyroX, tvGyroY, tvGyroZ;
    private TextView tvPitch, tvRoll, tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI Elements
        tvAccelX = findViewById(R.id.tvAccelX);
        tvAccelY = findViewById(R.id.tvAccelY);
        tvAccelZ = findViewById(R.id.tvAccelZ);
        tvGyroX = findViewById(R.id.tvGyroX);
        tvGyroY = findViewById(R.id.tvGyroY);
        tvGyroZ = findViewById(R.id.tvGyroZ);
        tvPitch = findViewById(R.id.tvPitch);
        tvRoll = findViewById(R.id.tvRoll);
        tvStatus = findViewById(R.id.tvStatus);

        // Initialize SensorManager and Sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }

        // Handle missing sensors gracefully
        if (accelerometer == null) {
            tvAccelX.setText(R.string.sensor_error);
        }
        if (gyroscope == null) {
            tvGyroX.setText(R.string.sensor_error);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register listeners when the activity is active
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
        if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister listeners to avoid battery drain when app is in the background
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float ax = event.values[0];
            float ay = event.values[1];
            float az = event.values[2];

            // Display raw accelerometer data
            tvAccelX.setText(getString(R.string.accel_x, ax));
            tvAccelY.setText(getString(R.string.accel_y, ay));
            tvAccelZ.setText(getString(R.string.accel_z, az));

            // Estimate Orientation (Pitch and Roll)
            estimateOrientation(ax, ay, az);

        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float gx = event.values[0];
            float gy = event.values[1];
            float gz = event.values[2];

            // Display raw gyroscope data
            tvGyroX.setText(getString(R.string.gyro_x, gx));
            tvGyroY.setText(getString(R.string.gyro_y, gy));
            tvGyroZ.setText(getString(R.string.gyro_z, gz));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for this basic implementation
    }

    private void estimateOrientation(float x, float y, float z) {
        // Calculate pitch and roll in degrees
        int pitch = (int) (Math.atan2(y, Math.sqrt(x * x + z * z)) * 180 / Math.PI);
        int roll = (int) (Math.atan2(-x, z) * 180 / Math.PI);

        tvPitch.setText(getString(R.string.pitch_label, pitch));
        tvRoll.setText(getString(R.string.roll_label, roll));

        // Textual orientation description
        if (Math.abs(pitch) > 60) {
            tvStatus.setText(R.string.status_upright);
        } else if (Math.abs(pitch) < 20 && Math.abs(roll) < 20) {
            tvStatus.setText(R.string.status_flat);
        } else {
            tvStatus.setText(R.string.status_tilted);
        }
    }
}