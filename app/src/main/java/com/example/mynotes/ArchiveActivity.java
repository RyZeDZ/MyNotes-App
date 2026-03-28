package com.example.mynotes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ArchiveActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NoteAdapter adapter;
    List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        notes = new ArrayList<>();
        notes.add(new Note("Old project ideas", "UI mockups from last semester", "Mar 10"));
        notes.add(new Note("Exam schedule S5", "Finals: Jan 15 to Jan 28", "Jan 5"));
        notes.add(new Note("Recipe -- Tiramisu", "Traditional Friday recipe...", "Dec 20"));
        notes.add(new Note("Network notes", "TCP/IP, OSI model summary", "Nov 30"));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NoteAdapter(notes);
        recyclerView.setAdapter(adapter);
    }
}
