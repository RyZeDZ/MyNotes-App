package com.example.mynotes;

import android.content.Intent;
import android.os.Bundle;

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
        setContentView(R.layout.activity_archive);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NoteViewModel viewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        viewModel.init(this);

        viewModel.getArchivedNotes().observe(this, noteEntities -> {
            List<Note> notes = new ArrayList<>();
            for (NoteEntity entity : noteEntities) {
                NoteAction note = new NoteAction(
                        entity.title,
                        entity.content.length() > 50 ? entity.content.substring(0, 50) + "..." : entity.content,
                        entity.date,
                        "Unarchive",
                        R.color.primary,
                        R.color.textSecondary,
                        R.color.green
                );
                note.id = entity.id;
                note.fullContent = entity.content;
                notes.add(note);
            }
            adapter = new NoteAdapter(notes, new NoteAdapter.ActionCallback() {
                @Override
                public void onActionClick(String noteId) {
                    viewModel.getNoteById(noteId).observe(ArchiveActivity.this, note -> {
                        if (note != null) viewModel.restoreNote(note);
                    });
                }
            });
            recyclerView.setAdapter(adapter);
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_archive);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_notes) {
                startActivity(new Intent(this, ListActivity.class));
                finish();
            } else if (id == R.id.nav_archive) {
                return true;
            } else if (id == R.id.nav_trash) {
                startActivity(new Intent(this, TrashActivity.class));
                finish();
            }
            return true;
        });
    }
}