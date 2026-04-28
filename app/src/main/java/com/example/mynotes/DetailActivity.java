package com.example.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FloatingActionButton btnShare = findViewById(R.id.btnShare);
        EditText etTitle = findViewById(R.id.etTitle);
        EditText etContent = findViewById(R.id.etContent);
        btnShare.setOnClickListener(v -> {
            String noteTitle = etTitle.getText().toString().trim();
            String noteContent = etContent.getText().toString().trim();
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, noteTitle);
            shareIntent.putExtra(Intent.EXTRA_TEXT, noteTitle + "\n\n" + noteContent);
            startActivity(Intent.createChooser(shareIntent, "Share note via"));
        });
    }
}