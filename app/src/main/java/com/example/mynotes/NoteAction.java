package com.example.mynotes;

public class NoteAction extends Note {
    int buttonColorResId;

    public NoteAction(String title, String subtitle, String date, int colorResId, int dateColorResId, int buttonColorResId) {
        super(title, subtitle, date, colorResId, dateColorResId);
        this.buttonColorResId = buttonColorResId;
    }
}
