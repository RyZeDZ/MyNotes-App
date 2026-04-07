package com.example.mynotes;

public class Topic {
    String name;
    String emoji;
    int noteCount;
    int iconBgColor;

    public Topic(String name, String emoji, int noteCount, int iconBgColor) {
        this.name = name;
        this.emoji = emoji;
        this.noteCount = noteCount;
        this.iconBgColor = iconBgColor;
    }
}