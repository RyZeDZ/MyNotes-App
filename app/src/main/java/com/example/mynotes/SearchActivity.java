package com.example.mynotes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        NoteViewModel viewModel = new ViewModelProvider(this).get(NoteViewModel.class);viewModel.init(this);// replace etSearch with the actual ID of the search EditTextetSearch.addTextChangedListener(new TextWatcher() { @Override public void onTextChanged(CharSequence s, int start, int before, int count) { String query = s.toString().trim(); if (!query.isEmpty()) { viewModel.searchNotes(query).observe(SearchActivity.this, results -> { List<Note> notes = new ArrayList<>(); for (NoteEntity entity : results) { notes.add(new NoteLabel( entity.title, entity.content, entity.date, R.color.primary, R.color.textSecondary, new ArrayList<>(), new ArrayList<>() )); } NoteAdapter adapter = new NoteAdapter(notes); recyclerView.setAdapter(adapter); // update result count text tvResultCount.setText(results.size() + " RESULTS FOR \"" + query.toUpperCase() + "\""); }); } } @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {} @Override public void afterTextChanged(android.text.Editable s) {}});
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NoteAdapter(notes);
        recyclerView.setAdapter(adapter);
    }
}