package com.example.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ArchiveActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);

        NoteViewModel viewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        viewModel.init(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewModel.getArchivedNotes().observe(this, noteEntities -> {
            List<Note> notes = new ArrayList<>();
            for (NoteEntity entity : noteEntities) {
                notes.add(new NoteAction(
                        entity.title,
                        entity.content,
                        entity.date,
                        "Restore",
                        R.color.primary,
                        R.color.textSecondary,
                        R.color.primary
                ));
            }
            adapter = new NoteAdapter(notes);
            recyclerView.setAdapter(adapter);
            TextView tvCount = findViewById(R.id.tvArchivedCount);
            tvCount.setText(noteEntities.size() + " TRASH NOTES");
        });
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_trash);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_notes) {
                startActivity(new Intent(this, ListActivity.class));
                finish();
            } else if (id == R.id.nav_archive) {
                startActivity(new Intent(this, ArchiveActivity.class));
                finish();
            } else if (id == R.id.nav_trash) {
                startActivity(new Intent(this, TrashActivity.class));
                finish();
            }
            return true;
        });
    }
}