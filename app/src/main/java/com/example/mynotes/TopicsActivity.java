package com.example.mynotes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

        topics = new ArrayList<>();
        topics.add(new Topic("Study", "📚", 8, R.color.topicStudy));
        topics.add(new Topic("Work", "💼", 5, R.color.topicWork));
        topics.add(new Topic("Personal", "🏠", 12, R.color.topicPersonal));
        topics.add(new Topic("Ideas", "💡", 3, R.color.topicIdeas));
        topics.add(new Topic("Projects", "🎯", 6, R.color.topicProjects));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TopicAdapter(topics);
        recyclerView.setAdapter(adapter);
    }
}