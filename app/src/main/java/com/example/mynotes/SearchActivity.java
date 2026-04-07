package com.example.mynotes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NoteAdapter adapter;
    List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        notes = new ArrayList<>();
        notes.add(new NoteLabel("Mobile dev TP", "Notes app: add, edit, archive...", "Mar 27", R.color.green, R.color.textSecondary, List.of("Study"), List.of(R.color.green)));
        notes.add(new NoteLabel("Android for mobile", "Install android studio...", "Mar 25", R.color.black, R.color.textSecondary, List.of("Ideas"), List.of(R.color.black)));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NoteAdapter(notes);
        recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_notes);
    }
}