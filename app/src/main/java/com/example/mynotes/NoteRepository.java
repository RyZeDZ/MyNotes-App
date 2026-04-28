package com.example.mynotes;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Executors;

public class NoteRepository {

    private NoteDao noteDao;
    private FirebaseFirestore db;
    private String userId;

    public NoteRepository(Context context) {
        noteDao = AppDatabase.getInstance(context).noteDao();
        db = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) userId = user.getUid();
    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return noteDao.getAllNotes(userId);
    }

    public LiveData<List<NoteEntity>> getArchivedNotes() {
        return noteDao.getArchivedNotes(userId);
    }

    public LiveData<List<NoteEntity>> getTrashedNotes() {
        return noteDao.getTrashedNotes(userId);
    }

    public LiveData<List<NoteEntity>> searchNotes(String query) {
        return noteDao.searchNotes(userId, query);
    }

    public void insertNote(NoteEntity note) {
        note.userId = userId;
        note.id = UUID.randomUUID().toString();
        note.createdAt = System.currentTimeMillis();
        note.date = new SimpleDateFormat("MMM dd", Locale.getDefault())
                .format(new Date());

        Executors.newSingleThreadExecutor().execute(() -> noteDao.insert(note));

        db.collection("users").document(userId)
                .collection("notes").document(note.id)
                .set(note);
    }

    public void updateNote(NoteEntity note) {
        Executors.newSingleThreadExecutor().execute(() -> noteDao.update(note));

        db.collection("users").document(userId)
                .collection("notes").document(note.id)
                .set(note);
    }

    public void deleteNote(NoteEntity note) {
        Executors.newSingleThreadExecutor().execute(() -> noteDao.delete(note));

        db.collection("users").document(userId)
                .collection("notes").document(note.id)
                .delete();
    }

    public void archiveNote(NoteEntity note) {
        note.isArchived = true;
        updateNote(note);
    }

    public void trashNote(NoteEntity note) {
        note.isTrashed = true;
        updateNote(note);
    }

    public void restoreNote(NoteEntity note) {
        note.isArchived = false;
        note.isTrashed = false;
        updateNote(note);
    }

    public void archiveById(int noteId) {
        Executors.newSingleThreadExecutor().execute(() ->
                noteDao.archiveNote(noteId)
        );
    }

    public void unarchiveById(int noteId) {
        Executors.newSingleThreadExecutor().execute(() ->
                noteDao.unarchiveNote(noteId)
        );
    }

    public void emptyTrash() {
        Executors.newSingleThreadExecutor().execute(() ->
                noteDao.emptyTrash(userId)
        );
    }

    public void deleteOldTrashedNotes(long timeLimit) {
        Executors.newSingleThreadExecutor().execute(() ->
                noteDao.deleteOldTrashedNotes(timeLimit)
        );
    }

    public LiveData<List<NoteEntity>> getNotesWithReminders() {
        return noteDao.getNotesWithReminders();
    }

    public LiveData<List<NoteEntity>> getNotesByTopic(String topic) {
        return noteDao.getNotesByTopic(userId, topic);
    }

}