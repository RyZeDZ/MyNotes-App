package com.example.mynotes;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class NoteEntity {
    @PrimaryKey
    @NonNull
    public String id = "";
    public String title = "";
    public String content = "";
    public String date = "";
    public String topicId = "";
    public boolean isArchived = false;
    public boolean isTrashed = false;
    public String userId = "";
    public long createdAt = 0;
    public long deletedAt = 0;
    public long reminderTime = 0;
}