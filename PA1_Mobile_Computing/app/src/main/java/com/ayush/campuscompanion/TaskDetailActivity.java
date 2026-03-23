package com.ayush.campuscompanion;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class TaskDetailActivity extends AppCompatActivity {

    private int taskPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvDescription = findViewById(R.id.tvDetailDescription);
        Chip chipPriority = findViewById(R.id.chipPriority);
        MaterialButton btnMarkComplete = findViewById(R.id.btnMarkComplete);

        if (getIntent() == null || !getIntent().hasExtra(TaskListActivity.EXTRA_TASK)) {
            Toast.makeText(this, R.string.error_task_not_found, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Task task = (Task) getIntent().getSerializableExtra(TaskListActivity.EXTRA_TASK);
        taskPosition = getIntent().getIntExtra(TaskListActivity.EXTRA_TASK_POSITION, -1); // NEW: Catch position

        if (task != null) {
            tvTitle.setText(task.getTitle());
            tvDescription.setText(task.getDescription());
            chipPriority.setText(task.getPriority());

            // DECORATION LOGIC: If already completed, disable the button so they can't do it again
            if (task.isCompleted()) {
                btnMarkComplete.setEnabled(false);
                btnMarkComplete.setText("Task Completed ✅");
            }
        }

        btnMarkComplete.setOnClickListener(v -> showConfirmationDialog());
    }

    private void showConfirmationDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_confirm_title)
                .setMessage(R.string.dialog_confirm_message)
                .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(TaskListActivity.EXTRA_TASK_POSITION, taskPosition);
                        setResult(RESULT_OK, resultIntent); // RESULT_OK triggers the update!

                        Toast.makeText(TaskDetailActivity.this, R.string.toast_task_completed, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton(R.string.dialog_no, null)
                .show();
    }
}