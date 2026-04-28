package com.example.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TrashActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NoteViewModel viewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        viewModel.init(this);

        viewModel.getTrashedNotes().observe(this, noteEntities -> {
            List<Note> notes = new ArrayList<>();
            for (NoteEntity entity : noteEntities) {
                long daysLeft = getDaysLeft(entity.deletedAt);
                String label  = daysLeft <= 0 ? "Deleting soon" : daysLeft + (daysLeft == 1 ? " day left" : " days left");
                int dateColor = daysLeft <= 3 ? R.color.red : daysLeft <= 7 ? R.color.primary : R.color.disabled;

                NoteAction note = new NoteAction(
                        entity.title,
                        entity.content.length() > 50 ? entity.content.substring(0, 50) + "..." : entity.content,
                        label,
                        "Restore",
                        R.color.disabled,
                        dateColor,
                        R.color.primary
                );
                note.id = entity.id;
                note.fullContent = entity.content;
                notes.add(note);
            }
            adapter = new NoteAdapter(notes, noteId -> {
                viewModel.getNoteById(noteId).observe(TrashActivity.this, note -> {
                    if (note != null) viewModel.restoreNote(note);
                });
            });
            recyclerView.setAdapter(adapter);
        });

        MaterialButton btnEmptyAll = findViewById(R.id.btnEmptyAllTrash);
        btnEmptyAll.setOnClickListener(v -> {
            viewModel.getTrashedNotes().observe(this, noteEntities -> {
                for (NoteEntity entity : noteEntities) viewModel.deleteNote(entity);
                Toast.makeText(this, "Trash emptied", Toast.LENGTH_SHORT).show();
            });
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
            }
            return true;
        });
    }

    long getDaysLeft(long deletedAt) {
        if (deletedAt <= 0) return 30; // deletedAt not set, assume just trashed
        long elapsed = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - deletedAt);
        return 30 - elapsed;
    }
}