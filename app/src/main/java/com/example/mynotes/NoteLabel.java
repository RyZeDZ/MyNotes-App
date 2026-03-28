package com.example.mynotes;

import java.util.List;

public class NoteLabel extends Note {
    List<String> topics;
    List<Integer> topicColors;

    public NoteLabel(String title, String subtitle, String date, int colorResId, int dateColorResId, List<String> topics, List<Integer> topicColors) {
        super(title, subtitle, date, colorResId, dateColorResId);
        this.topics = topics;
        this.topicColors = topicColors;
    }
}