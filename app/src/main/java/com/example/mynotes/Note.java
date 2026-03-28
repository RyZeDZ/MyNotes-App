package com.example.mynotes;

public class Note {
    String title;
    String subtitle;
    String date;
    int colorResId;
    int buttonColorResId;

    public Note(String title, String subtitle, String date, int colorResId, int buttonColorResId) {
        this.title = title;
        this.subtitle = subtitle;
        this.date = date;
        this.colorResId = colorResId;
        this.buttonColorResId = buttonColorResId;
    }
}
