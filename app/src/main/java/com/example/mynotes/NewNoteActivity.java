package com.example.mynotes;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class NewNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnote);

        NoteViewModel viewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        viewModel.init(this);

        Button btnSave = findViewById(R.id.btnSave);
        EditText etTitle = findViewById(R.id.etTitle);
        EditText etContent = findViewById(R.id.etContent);
        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String content = etContent.getText().toString().trim();
            if (title.isEmpty()) {
                Toast.makeText(this, "Please add a title", Toast.LENGTH_SHORT).show();
                return;
            }
            NoteEntity note = new NoteEntity();
            note.title = title;
            note.content = content;
            viewModel.insertNote(note);
            finish();
        });
    }
}