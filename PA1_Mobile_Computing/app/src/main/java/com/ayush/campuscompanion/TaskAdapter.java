package com.ayush.campuscompanion;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private OnTaskClickListener listener;

    public interface OnTaskClickListener {
        void onTaskClick(Task task, int position);
    }

    public TaskAdapter(List<Task> taskList, OnTaskClickListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.tvPriority.setText(task.getPriority());

        if (task.isCompleted()) {
            holder.cardTask.setCardBackgroundColor(Color.parseColor("#E8F5E9")); // A nice light Material green
            holder.tvTitle.setText(task.getTitle() + " ✅"); // Add a checkmark
        } else {
            // Must reset to default for tasks that aren't done (RecyclerView recycles views!)
            holder.cardTask.setCardBackgroundColor(Color.WHITE);
            holder.tvTitle.setText(task.getTitle());
        }

        // Pass the position when clicked
        holder.itemView.setOnClickListener(v -> listener.onTaskClick(task, position));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardTask; // NEW: Reference to the card to change its color
        TextView tvTitle;
        TextView tvPriority;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            cardTask = itemView.findViewById(R.id.cardTask);
            tvTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvPriority = itemView.findViewById(R.id.tvTaskPriority);
        }
    }
}