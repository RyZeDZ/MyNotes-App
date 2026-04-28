package com.example.mynotes;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextInputEditText etSearch;
    TextView tvSearchCount;
    Chip chipAll, chipTitle, chipContent;
    NoteViewModel viewModel;
    NoteAdapter adapter;
    String activeFilter = "all";
    LiveData<List<NoteEntity>> currentLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerView);
        etSearch = findViewById(R.id.editText6);
        tvSearchCount = findViewById(R.id.tvSearchCount);
        chipAll = findViewById(R.id.chip);
        chipTitle = findViewById(R.id.chip2);
        chipContent = findViewById(R.id.chip3);

        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        viewModel.init(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        highlightChip(chipAll);

        chipAll.setOnClickListener(v -> {
            activeFilter = "all";
            highlightChip(chipAll);
            triggerSearch(getCurrentQuery());
        });
        chipTitle.setOnClickListener(v -> {
            activeFilter = "title";
            highlightChip(chipTitle);
            triggerSearch(getCurrentQuery());
        });
        chipContent.setOnClickListener(v -> {
            activeFilter = "content";
            highlightChip(chipContent);
            triggerSearch(getCurrentQuery());
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                triggerSearch(s.toString().trim());
            }
        });
    }

    void triggerSearch(String query) {
        if (currentLiveData != null) currentLiveData.removeObserver(searchObserver);

        if (query.isEmpty()) {
            adapter = new NoteAdapter(new ArrayList<>());
            recyclerView.setAdapter(adapter);
            tvSearchCount.setText("");
            return;
        }

        currentLiveData = viewModel.searchNotes(query);
        currentLiveData.observe(this, searchObserver);
    }

    Observer<List<NoteEntity>> searchObserver = entities -> {
        String query = getCurrentQuery();
        List<Note> notes = new ArrayList<>();

        for (NoteEntity e : entities) {
            if (activeFilter.equals("title") && !e.title.toLowerCase().contains(query.toLowerCase())) continue;
            if (activeFilter.equals("content") && !e.content.toLowerCase().contains(query.toLowerCase())) continue;

            NoteLabel label = new NoteLabel(
                    e.title, e.content, e.date,
                    R.color.primary, R.color.textSecondary,
                    new ArrayList<>(), new ArrayList<>()
            );
            label.id = e.id;
            label.fullContent = e.content;
            notes.add(label);
        }

        adapter = new NoteAdapter(notes);
        recyclerView.setAdapter(adapter);
        tvSearchCount.setText(notes.size() + " RESULTS FOR \"" + query.toUpperCase() + "\"");
    };

    void highlightChip(Chip selected) {
        int primary = androidx.core.content.ContextCompat.getColor(this, R.color.primary);
        int cardBg  = androidx.core.content.ContextCompat.getColor(this, R.color.cardBg);
        chipAll.setChipBackgroundColor(ColorStateList.valueOf(chipAll == selected ? primary : cardBg));
        chipTitle.setChipBackgroundColor(ColorStateList.valueOf(chipTitle == selected ? primary : cardBg));
        chipContent.setChipBackgroundColor(ColorStateList.valueOf(chipContent == selected ? primary : cardBg));
    }

    String getCurrentQuery() {
        Editable text = etSearch.getText();
        return text != null ? text.toString().trim() : "";
    }
}