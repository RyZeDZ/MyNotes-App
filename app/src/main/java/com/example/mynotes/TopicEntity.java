package com.example.mynotes;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "topics")
public class TopicEntity {
    @PrimaryKey
    @NonNull
    public String id = "";
    public String name = "";
    public String emoji = "";
    public int colorResId = 0;
    public String userId = "";
}