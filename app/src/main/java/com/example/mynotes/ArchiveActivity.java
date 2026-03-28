package com.example.mynotes;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        notes.add(new Note("Old project ideas", "UI mockups from last semester", "Mar 10", R.color.disabled, R.color.green));
        notes.add(new Note("Exam schedule S5", "Finals: Jan 15 to Jan 28", "Jan 5", R.color.disabled, R.color.green));
        notes.add(new Note("Recipe -- Tiramisu", "Traditional Friday recipe...", "Dec 20", R.color.disabled, R.color.green));
        notes.add(new Note("Network notes", "TCP/IP, OSI model summary", "Nov 30", R.color.disabled, R.color.green));
        notes.add(new Note("Old project ideas", "UI mockups from last semester", "Mar 10", R.color.disabled, R.color.green));
        notes.add(new Note("Exam schedule S5", "Finals: Jan 15 to Jan 28", "Jan 5", R.color.disabled, R.color.green));
        notes.add(new Note("Recipe -- Tiramisu", "Traditional Friday recipe...", "Dec 20", R.color.disabled, R.color.green));
        notes.add(new Note("Network notes", "TCP/IP, OSI model summary", "Nov 30", R.color.disabled, R.color.green));
        notes.add(new Note("Old project ideas", "UI mockups from last semester", "Mar 10", R.color.disabled, R.color.green));
        notes.add(new Note("Exam schedule S5", "Finals: Jan 15 to Jan 28", "Jan 5", R.color.disabled, R.color.green));
        notes.add(new Note("Recipe -- Tiramisu", "Traditional Friday recipe...", "Dec 20", R.color.disabled, R.color.green));
        notes.add(new Note("Network notes", "TCP/IP, OSI model summary", "Nov 30", R.color.disabled, R.color.green));

        TextView tvArchivedCount = findViewById(R.id.tvArchivedCount);
        tvArchivedCount.setText(getString(R.string.label_archived_count, notes.size()));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NoteAdapter(notes);
        recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_archive);
    }
}
