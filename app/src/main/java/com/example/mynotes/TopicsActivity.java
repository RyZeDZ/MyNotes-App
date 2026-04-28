package com.example.mynotes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class TopicsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TopicAdapter adapter;
    List<Topic> topics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> finish());

        TopicViewModel viewModel = new ViewModelProvider(this).get(TopicViewModel.class);
        viewModel.init(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewModel.getAllTopics().observe(this, topicEntities -> {
            List<Topic> topics = new ArrayList<>();
            for (TopicEntity entity : topicEntities) {
                topics.add(new Topic(entity.name, entity.emoji, 0, entity.colorResId));
            }
            TopicAdapter adapter = new TopicAdapter(topics);
            recyclerView.setAdapter(adapter);});

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TopicAdapter(topics);
        recyclerView.setAdapter(adapter);
    }
}