package com.example.mynotes;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class TopicsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TopicViewModel viewModel;

    String[] EMOJIS = {"📚", "💼", "🏠", "💡", "🎯", "🎨", "🏋️", "🎵", "✈️", "🍕"};
    int[] COLORS = {
            R.color.topicStudy, R.color.topicWork, R.color.topicPersonal,
            R.color.topicIdeas, R.color.topicProjects, R.color.topicStudy
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> finish());

        viewModel = new ViewModelProvider(this).get(TopicViewModel.class);
        viewModel.init(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getAllTopics().observe(this, topicEntities -> {
            List<Topic> topics = new ArrayList<>();
            for (TopicEntity entity : topicEntities) {
                topics.add(new Topic(entity.name, entity.emoji, 0, entity.colorResId));
            }

            TopicAdapter adapter = new TopicAdapter(topics,
                    position -> {
                        TopicEntity toDelete = topicEntities.get(position);
                        viewModel.deleteTopic(toDelete);
                        Toast.makeText(this, "Topic deleted", Toast.LENGTH_SHORT).show();
                    }
            );
            recyclerView.setAdapter(adapter);
        });
    }

    public void showCreateTopicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Topic");

        EditText input = new EditText(this);
        input.setHint("Topic name (e.g. Work)");
        input.setPadding(48, 24, 48, 24);
        builder.setView(input);

        builder.setPositiveButton("Create", (dialog, which) -> {
            String name = input.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
                return;
            }
            TopicEntity entity = new TopicEntity();
            entity.name = name;
            entity.emoji = EMOJIS[(int)(Math.random() * EMOJIS.length)];
            entity.colorResId = COLORS[(int)(Math.random() * COLORS.length)];
            viewModel.insertTopic(entity);
            Toast.makeText(this, "Topic created", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Cancel", (d, w) -> d.dismiss());
        builder.show();
    }
}