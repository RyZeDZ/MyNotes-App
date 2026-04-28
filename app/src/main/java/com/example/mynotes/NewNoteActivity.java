package com.example.mynotes;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;

import java.util.Locale;

public class NewNoteActivity extends AppCompatActivity {

    NoteViewModel viewModel;
    EditText etTitle, etContent;
    String noteId = null;
    boolean isEditing = false;

    private long selectedReminderTime = 0;

    private String selectedTopicId = null;
    private TopicViewModel topicViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnote);

        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        viewModel.init(this);

        topicViewModel = new ViewModelProvider(this).get(TopicViewModel.class);
        topicViewModel.init(this);

        Chip chipAddTopic = findViewById(R.id.chipAddTopic);
        chipAddTopic.setOnClickListener(v -> showTopicPicker());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        ImageButton btnReminder = findViewById(R.id.btnReminder);
        btnReminder.setOnClickListener(v -> showDateTimePicker());

        ImageButton btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String content = etContent.getText().toString().trim();
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_SUBJECT, title);
            share.putExtra(Intent.EXTRA_TEXT, title + "\n\n" + content);
            startActivity(Intent.createChooser(share, "Share note via"));
        });

        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_save) {
                saveNote();
                return true;
            } else if (id == R.id.action_archive) {
                archiveNote();
                return true;
            } else if (id == R.id.action_delete) {
                deleteNote();
                return true;
            }
            return false;
        });

        MenuItem saveItem = toolbar.getMenu().findItem(R.id.action_save);
        SpannableString s = new SpannableString("Save");
        s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.primary)), 0, s.length(), 0);
        saveItem.setTitle(s);

        noteId = getIntent().getStringExtra("note_id");
        isEditing = noteId != null;

        if (isEditing) {
            toolbar.setTitle("Edit Note");
            etTitle.setText(getIntent().getStringExtra("note_title"));
            etContent.setText(getIntent().getStringExtra("note_content"));
            etTitle.setSelection(etTitle.getText().length());
        } else {
            toolbar.setTitle("New Note");
        }
    }

    private void saveNote() {
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();

        if (title.isEmpty()) {
            Toast.makeText(this, "Please add a title", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isEditing) {
            viewModel.getNoteById(noteId).observe(this, existingNote -> {
                if (existingNote != null) {
                    existingNote.title = title;
                    existingNote.content = content;
                    existingNote.topicId = selectedTopicId != null ? selectedTopicId : "";
                    existingNote.reminderTime = selectedReminderTime;

                    if (selectedReminderTime > 0) {
                        ReminderHelper.scheduleReminder(this, existingNote.id, existingNote.title, selectedReminderTime);
                    }
                    viewModel.updateNote(existingNote);
                    Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            NoteEntity note = new NoteEntity();
            note.title = title;
            note.content = content;
            note.topicId = selectedTopicId != null ? selectedTopicId : "";
            note.reminderTime = selectedReminderTime;

            if (selectedReminderTime > 0) {
                ReminderHelper.scheduleReminder(this, note.id, note.title, selectedReminderTime);
            }
            viewModel.insertNote(note);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void archiveNote() {
        if (!isEditing) {
            Toast.makeText(this, "Save the note first", Toast.LENGTH_SHORT).show();
            return;
        }
        viewModel.getNoteById(noteId).observe(this, note -> {
            if (note != null) {
                viewModel.archiveNote(note);
                Toast.makeText(this, "Note archived", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void deleteNote() {
        if (!isEditing) {
            Toast.makeText(this, "Save the note first", Toast.LENGTH_SHORT).show();
            return;
        }
        viewModel.getNoteById(noteId).observe(this, note -> {
            if (note != null) {
                viewModel.trashNote(note);
                Toast.makeText(this, "Note moved to trash", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void showDateTimePicker() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePicker = new DatePickerDialog(this,
                (view, year, month, day) -> {
                    calendar.set(year, month, day);

                    TimePickerDialog timePicker = new TimePickerDialog(this,
                            (view2, hour, minute) -> {
                                calendar.set(Calendar.HOUR_OF_DAY, hour);
                                calendar.set(Calendar.MINUTE, minute);
                                calendar.set(Calendar.SECOND, 0);

                                selectedReminderTime = calendar.getTimeInMillis();
                                Toast.makeText(this,
                                        "Reminder set for " + new SimpleDateFormat("MMM dd, HH:mm",
                                                Locale.getDefault()).format(calendar.getTime()),
                                        Toast.LENGTH_SHORT).show();
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                    );
                    timePicker.show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePicker.show();
    }

    private void showTopicPicker() {
        topicViewModel.getAllTopics().observe(this, topicEntities -> {
            if (topicEntities.isEmpty()) {
                Toast.makeText(this, "No topics yet. Create one in Topics screen.", Toast.LENGTH_SHORT).show();
                return;
            }

            String[] names = new String[topicEntities.size()];
            for (int i = 0; i < topicEntities.size(); i++) {
                names[i] = topicEntities.get(i).emoji + " " + topicEntities.get(i).name;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Choose a topic")
                    .setItems(names, (dialog, which) -> {
                        TopicEntity selected = topicEntities.get(which);
                        selectedTopicId = selected.id;

                        Chip chipAddTopic = findViewById(R.id.chipAddTopic);
                        chipAddTopic.setText(selected.emoji + " " + selected.name);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }
}