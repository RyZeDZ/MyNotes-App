package com.example.mynotes;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NoteAdapter adapter;
    NoteViewModel noteViewModel;
    TopicViewModel topicViewModel;
    ChipGroup chipGroup;
    String selectedTopicId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chipGroup = findViewById(R.id.chipGroup2);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.init(this);

        topicViewModel = new ViewModelProvider(this).get(TopicViewModel.class);
        topicViewModel.init(this);

        loadNotes(null);
        topicViewModel.getAllTopics().observe(this, topicEntities -> {
            chipGroup.removeAllViews();

            Chip chipAll = new Chip(this);
            chipAll.setId(R.id.chipAll);
            chipAll.setText("All");
            chipAll.setCheckable(true);
            chipAll.setChecked(selectedTopicId == null);
            chipAll.setChipBackgroundColor(ColorStateList.valueOf(
                    selectedTopicId == null ?
                            ContextCompat.getColor(this, R.color.primary) :
                            ContextCompat.getColor(this, R.color.disabledLight)
            ));
            chipAll.setTextColor(selectedTopicId == null ?
                    ContextCompat.getColor(this, R.color.white) :
                    ContextCompat.getColor(this, R.color.textSecondary)
            );
            chipAll.setOnClickListener(v -> {
                selectedTopicId = null;
                loadNotes(null);
                refreshChipColors(null, topicEntities);
            });
            chipGroup.addView(chipAll);

            for (TopicEntity entity : topicEntities) {
                Chip chip = new Chip(this);
                chip.setText(entity.emoji + " " + entity.name);
                chip.setCheckable(true);
                chip.setChecked(entity.id.equals(selectedTopicId));
                chip.setChipBackgroundColor(ColorStateList.valueOf(
                        entity.id.equals(selectedTopicId) ?
                                ContextCompat.getColor(this, R.color.primary) :
                                ContextCompat.getColor(this, R.color.disabledLight)
                ));
                chip.setTextColor(entity.id.equals(selectedTopicId) ?
                        ContextCompat.getColor(this, R.color.white) :
                        ContextCompat.getColor(this, R.color.textSecondary)
                );
                chip.setOnClickListener(v -> {
                    selectedTopicId = entity.id;
                    loadNotes(entity.id);
                    refreshChipColors(entity.id, topicEntities);
                });
                chipGroup.addView(chip);
            }

            Chip chipNew = new Chip(this);
            chipNew.setId(R.id.chipNew);
            chipNew.setText("+ New");
            chipNew.setTextColor(ContextCompat.getColor(this, R.color.textSecondary));
            chipNew.setChipBackgroundColor(ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.disabledLight)
            ));
            chipNew.setOnClickListener(v ->
                    startActivity(new Intent(this, TopicsActivity.class))
            );
            chipGroup.addView(chipNew);
        });

        TextView tvSearch = findViewById(R.id.tvSearch);
        ImageView ivSettings = findViewById(R.id.ivSettings);
        FloatingActionButton fabNewNote = findViewById(R.id.fabNewNote);

        ivSettings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
        tvSearch.setOnClickListener(v -> startActivity(new Intent(this, SearchActivity.class)));
        fabNewNote.setOnClickListener(v -> startActivity(new Intent(this, NewNoteActivity.class)));

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_notes);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_notes) return true;
            else if (id == R.id.nav_archive) {
                startActivity(new Intent(this, ArchiveActivity.class));
                finish();
            } else if (id == R.id.nav_trash) {
                startActivity(new Intent(this, TrashActivity.class));
                finish();
            }
            return true;
        });
    }

    private void loadNotes(String topicId) {
        LiveData<List<NoteEntity>> source = topicId == null
                ? noteViewModel.getAllNotes()
                : noteViewModel.getNotesByTopic(topicId);

        source.observe(this, noteEntities -> {
            List<Note> notes = new ArrayList<>();
            for (NoteEntity entity : noteEntities) {
                String preview = entity.content.length() > 50
                        ? entity.content.substring(0, 50) + "..."
                        : entity.content;
                NoteLabel label = new NoteLabel(
                        entity.title, preview, entity.date,
                        R.color.primary, R.color.textSecondary,
                        new ArrayList<>(), new ArrayList<>()
                );
                label.id = entity.id;
                label.fullContent = entity.content;
                notes.add(label);
            }
            adapter = new NoteAdapter(notes);
            recyclerView.setAdapter(adapter);
        });
    }

    private void refreshChipColors(String selectedId, List<TopicEntity> entities) {
        topicViewModel.getAllTopics().observe(this, t -> {});
    }
}