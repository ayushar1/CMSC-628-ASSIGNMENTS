package com.ayush.cloudnotes;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.*;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.*;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.*;
import com.amazonaws.services.cognitoidentityprovider.model.SignUpResult;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etEmail;
    private TextView tvStatus;
    private CognitoHelper cognitoHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cognitoHelper = new CognitoHelper(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail    = findViewById(R.id.etEmail);
        tvStatus   = findViewById(R.id.tvStatus);

        findViewById(R.id.btnLogin).setOnClickListener(v -> signIn());
        findViewById(R.id.btnSignUp).setOnClickListener(v -> signUp());
    }

    private void signIn() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showStatus("Please enter username and password");
            return;
        }
        showStatus("Signing in...");

        cognitoHelper.signIn(username, password, new AuthenticationHandler() {
            @Override
            public void onSuccess(CognitoUserSession session, CognitoDevice device) {
                runOnUiThread(() -> {
                    showStatus("Sign in successful!");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                });
            }

            @Override
            public void getAuthenticationDetails(
                    AuthenticationContinuation cont, String userId) {
                AuthenticationDetails details = new AuthenticationDetails(
                        userId,
                        etPassword.getText().toString().trim(),
                        null
                );
                cont.setAuthenticationDetails(details);
                cont.continueTask();
            }

            @Override
            public void getMFACode(MultiFactorAuthenticationContinuation cont) {}

            @Override
            public void authenticationChallenge(ChallengeContinuation cont) {}

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() ->
                        showStatus("Sign in failed: " + e.getMessage())
                );
            }
        });
    }

    private void signUp() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String email    = etEmail.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            showStatus("Please fill all fields for sign-up");
            return;
        }
        showStatus("Signing up...");

        cognitoHelper.signUp(username, password, email, new SignUpHandler() {
            @Override
            public void onSuccess(CognitoUser user, SignUpResult signUpResult) {
                runOnUiThread(() -> {
                    android.widget.Toast.makeText(LoginActivity.this,
                            "Sign up success! Showing dialog...",
                            android.widget.Toast.LENGTH_LONG).show();
                    showConfirmationDialog(
                            etUsername.getText().toString().trim()
                    );
                });
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() ->
                        showStatus("Sign-up failed: " + e.getMessage())
                );
            }
        });
    }

    private void showConfirmationDialog(String username) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Verify Your Email");
        builder.setMessage("Enter the 6-digit code sent to your email");

        final EditText input = new EditText(LoginActivity.this);
        input.setHint("6-digit code");
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Confirm", (dialog, which) -> {
            String code = input.getText().toString().trim();
            if (code.isEmpty()) {
                showStatus("Please enter the confirmation code");
                return;
            }
            cognitoHelper.confirmSignUp(username, code, new GenericHandler() {
                @Override
                public void onSuccess() {
                    runOnUiThread(() ->
                            showStatus("✅ Account confirmed! You can now sign in.")
                    );
                }

                @Override
                public void onFailure(Exception e) {
                    runOnUiThread(() ->
                            showStatus("Confirmation failed: " + e.getMessage())
                    );
                }
            });
        });

        builder.setNegativeButton("Cancel", (dialog, which) ->
                showStatus("Sign-up successful! Confirm your email before signing in.")
        );

        builder.setCancelable(false);
        builder.show();
    }

    private void showStatus(String msg) {
        tvStatus.setText(msg);
    }
}