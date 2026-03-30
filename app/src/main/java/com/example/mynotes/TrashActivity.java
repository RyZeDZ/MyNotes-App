package com.example.mynotes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class TrashActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NoteAdapter adapter;
    List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);

        notes = new ArrayList<>();
        notes.add(new NoteAction("Drafts", "Just some random drafts...", "3 days left", "Restore", R.color.disabled, R.color.red, R.color.primary));
        notes.add(new NoteAction("Old todo", "check on the team...", "13 days left", "Restore", R.color.disabled, R.color.red, R.color.primary));
        notes.add(new NoteAction("Shopping list", "New laptop, new pc...", "14 days left", "Restore", R.color.disabled, R.color.red, R.color.primary));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NoteAdapter(notes);
        recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_trash);
    }
}