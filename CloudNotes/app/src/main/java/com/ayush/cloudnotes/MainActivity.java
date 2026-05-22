package com.ayush.cloudnotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private EditText etNoteContent;
    private TextView tvNotesList;
    private CognitoHelper cognitoHelper;
    private DynamoDBHelper dynamoDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cognitoHelper  = new CognitoHelper(this);
        dynamoDBHelper = new DynamoDBHelper();

        etNoteContent = findViewById(R.id.etNoteContent);
        tvNotesList   = findViewById(R.id.tvNotesList);

        findViewById(R.id.btnSaveNote).setOnClickListener(v -> saveNote());
        findViewById(R.id.btnLoadNotes).setOnClickListener(v -> loadNotes());
        findViewById(R.id.btnLogout).setOnClickListener(v -> logout());
    }

    private void saveNote() {
        String content = etNoteContent.getText().toString().trim();
        if (content.isEmpty()) {
            Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId    = CognitoHelper.getLoggedInUserId();
        String noteId    = UUID.randomUUID().toString();
        String timestamp = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault()
        ).format(new Date());

        Note note = new Note(noteId, userId, content, timestamp);

        new Thread(() -> {
            try {
                dynamoDBHelper.saveNote(note);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();
                    etNoteContent.setText("");
                });
            } catch (Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(this,
                                "Error saving note: " + e.getMessage(),
                                Toast.LENGTH_LONG).show()
                );
            }
        }).start();
    }

    private void loadNotes() {
        String userId = CognitoHelper.getLoggedInUserId();
        tvNotesList.setText("Loading...");

        new Thread(() -> {
            try {
                List<Note> notes = dynamoDBHelper.getNotesForUser(userId);
                StringBuilder sb = new StringBuilder();

                if (notes.isEmpty()) {
                    sb.append("No notes found.");
                } else {
                    for (Note n : notes) {
                        sb.append("📝 ").append(n.getContent())
                                .append("\n   ").append(n.getTimestamp())
                                .append("\n\n");
                    }
                }
                runOnUiThread(() -> tvNotesList.setText(sb.toString()));
            } catch (Exception e) {
                runOnUiThread(() ->
                        tvNotesList.setText("Error loading notes: " + e.getMessage())
                );
            }
        }).start();
    }

    private void logout() {
        cognitoHelper.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}