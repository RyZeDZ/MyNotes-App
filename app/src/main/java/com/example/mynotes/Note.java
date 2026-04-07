package com.example.mynotes;

public class Note {
    String title;
    String subtitle;
    String date;
    int colorResId;
    int dateColorResId;

    public Note(String title, String subtitle, String date, int colorResId, int dateColorResId) {
        this.title = title;
        this.subtitle = subtitle;
        this.date = date;
        this.colorResId = colorResId;
        this.dateColorResId = dateColorResId;
    }
}
