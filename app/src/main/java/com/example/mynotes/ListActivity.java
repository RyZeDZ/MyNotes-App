package com.example.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NoteAdapter adapter;
    List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        notes = new ArrayList<>();
        notes.add(new NoteLabel("Meeting Calendar", "UI mockups from last semester", "Mar 10", R.color.primary, R.color.textSecondary, List.of("Work"), List.of(R.color.primary)));
        notes.add(new NoteLabel("Meeting Calendar", "UI mockups from last semester", "Mar 10", R.color.black, R.color.textSecondary, List.of("Study"), List.of(R.color.black)));
        notes.add(new NoteLabel("Meeting Calendar", "UI mockups from last semester", "Mar 10", R.color.green, R.color.textSecondary, List.of("Idea"), List.of(R.color.green)));
        notes.add(new NoteLabel("Meeting Calendar", "UI mockups from last semester", "Mar 10", R.color.red, R.color.textSecondary, List.of("Personal"), List.of(R.color.red)));
        notes.add(new NoteLabel("Calendar", "UI mockups from next semester", "Mar 11", R.color.red, R.color.textSecondary, List.of("Personal"), List.of(R.color.red)));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NoteAdapter(notes);
        recyclerView.setAdapter(adapter);

        TextView tvSearch = findViewById(R.id.tvSearch);
        ImageView ivSettings = findViewById(R.id.ivSettings);
        Chip chipNew = findViewById(R.id.chipNew);
        FloatingActionButton fabNewNote = findViewById(R.id.fabNewNote);
        ivSettings.setOnClickListener(v -> {
            startActivity(new Intent(this, SettingsActivity.class));
        });
        tvSearch.setOnClickListener(v -> {
            startActivity(new Intent(this, SearchActivity.class));
        });
        chipNew.setOnClickListener(v -> {
            startActivity(new Intent(this, TopicsActivity.class));
        });
        fabNewNote.setOnClickListener(v -> {
            startActivity(new Intent(this, DetailActivity.class));
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_notes);
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