package com.ayush.campuscompanion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    public static final String EXTRA_TASK = "extra_task";
    public static final String EXTRA_TASK_POSITION = "extra_task_position"; // NEW: Pass the position

    private TaskAdapter adapter;
    private List<Task> tasks;

    private final ActivityResultLauncher<Intent> detailActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    // Get the position of the task that was completed
                    int position = result.getData().getIntExtra(EXTRA_TASK_POSITION, -1);
                    if (position != -1) {
                        // Mark it as done and tell the adapter to refresh that specific row so it turns green!
                        tasks.get(position).setCompleted(true);
                        adapter.notifyItemChanged(position);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        TextView tvWelcomeMessage = findViewById(R.id.tvWelcomeMessage);
        RecyclerView rvTasks = findViewById(R.id.rvTasks);

        String userName = getIntent().getStringExtra(HomeActivity.EXTRA_USER_NAME);
        if (userName == null || userName.isEmpty()) userName = "Student";
        tvWelcomeMessage.setText(getString(R.string.welcome_message, userName));

        rvTasks.setLayoutManager(new LinearLayoutManager(this));

        // Generate tasks
        tasks = new ArrayList<>();
        tasks.add(new Task("Finish ML Assignment", "Complete the Bayes nets implementation.", "High"));
        tasks.add(new Task("Watch FC Barcelona Match", "Champions league game tonight.", "Medium"));
        tasks.add(new Task("Update Portfolio Website", "Add the new sports car recommender project.", "High"));
        tasks.add(new Task("Research Gaming Laptops", "Compare specs for the new purchase.", "Low"));
        tasks.add(new Task("Buy New Perfume", "Look for a deal on Calvin Klein or Nautica.", "Low"));
        tasks.add(new Task("Review Quantum Computing", "Read chapter 4 on algorithms.", "Medium"));
        tasks.add(new Task("Call Mom", "Catch up over the weekend.", "High"));
        tasks.add(new Task("Plan Chicago Trip", "Finalize Flight tickets for the day trip.", "Medium"));

        // Setup adapter with click listener
        adapter = new TaskAdapter(tasks, (task, position) -> {
            Intent intent = new Intent(TaskListActivity.this, TaskDetailActivity.class);
            intent.putExtra(EXTRA_TASK, task);
            intent.putExtra(EXTRA_TASK_POSITION, position); // Pass position to next screen

            detailActivityLauncher.launch(intent);
        });

        rvTasks.setAdapter(adapter);
    }
}