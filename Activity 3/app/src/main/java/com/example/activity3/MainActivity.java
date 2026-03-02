package com.example.activity3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.yourappname.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextInput = findViewById(R.id.editTextInput);
        Button buttonSend = findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextInput.getText().toString();

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                intent.putExtra(EXTRA_MESSAGE, message);

                startActivity(intent);
            }
        });
    }
}