package com.ayush.campuscompanion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "LifecycleLogging"; // For grading requirement
    private static final String STATE_USER_NAME = "state_user_name";
    public static final String EXTRA_USER_NAME = "extra_user_name";

    private TextInputLayout tilName;
    private TextInputEditText etName;
    private MaterialButton btnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d(TAG, "HomeActivity: onCreate called");

        // Initialize Views
        tilName = findViewById(R.id.tilName);
        etName = findViewById(R.id.etName);
        btnEnter = findViewById(R.id.btnEnter);

        // Restore state if rotating the screen
        if (savedInstanceState != null) {
            String savedName = savedInstanceState.getString(STATE_USER_NAME);
            if (savedName != null) {
                etName.setText(savedName);
            }
        }

        // Handle Button Click
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etName.getText().toString().trim();

                // Validate non-empty input
                if (userName.isEmpty()) {
                    tilName.setError(getString(R.string.error_empty_name));
                } else {
                    tilName.setError(null); // Clear error

                    Intent intent = new Intent(HomeActivity.this, TaskListActivity.class);
                    intent.putExtra(EXTRA_USER_NAME, userName); // Pass data using Intent extras
                    startActivity(intent);
                }
            }
        });
    }

    // Preserve user name across rotation
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "HomeActivity: onSaveInstanceState called");
        if (etName.getText() != null) {
            outState.putString(STATE_USER_NAME, etName.getText().toString());
        }
    }

    // --- Lifecycle Logging Requirements ---

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "HomeActivity: onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "HomeActivity: onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "HomeActivity: onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "HomeActivity: onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "HomeActivity: onDestroy called");
    }
}