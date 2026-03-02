package com.example.activity3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();

        String passedMessage = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView textViewDisplay = findViewById(R.id.textViewDisplay);

        if (passedMessage != null && !passedMessage.isEmpty()) {
            textViewDisplay.setText(passedMessage);
        } else {
            textViewDisplay.setText("No message received.");
        }
    }
}