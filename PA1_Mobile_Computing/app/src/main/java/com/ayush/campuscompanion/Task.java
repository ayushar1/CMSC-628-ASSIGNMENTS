package com.ayush.campuscompanion;

import java.io.Serializable;

public class Task implements Serializable {
    private String title;
    private String description;
    private String priority;
    private boolean isCompleted;

    public Task(String title, String description, String priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.isCompleted = false; // Default= false
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}