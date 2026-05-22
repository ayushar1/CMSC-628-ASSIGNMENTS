package com.ayush.cloudnotes;

public class Note {
    private String noteId;
    private String userId;
    private String content;
    private String timestamp;

    public Note() {}

    public Note(String noteId, String userId, String content, String timestamp) {
        this.noteId = noteId;
        this.userId = userId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getNoteId()    { return noteId; }
    public String getUserId()    { return userId; }
    public String getContent()   { return content; }
    public String getTimestamp() { return timestamp; }

    public void setNoteId(String noteId)       { this.noteId = noteId; }
    public void setUserId(String userId)       { this.userId = userId; }
    public void setContent(String content)     { this.content = content; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
