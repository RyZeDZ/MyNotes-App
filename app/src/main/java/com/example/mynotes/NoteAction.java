package com.example.mynotes;

public class NoteAction extends Note {
    String buttonText;
    int buttonColorResId;

    public NoteAction(String title, String subtitle, String date, String buttonText, int colorResId, int dateColorResId, int buttonColorResId) {
        super(title, subtitle, date, colorResId, dateColorResId);
        this.buttonText = buttonText;
        this.buttonColorResId = buttonColorResId;
    }
}
