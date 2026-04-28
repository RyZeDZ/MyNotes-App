package com.example.mynotes;
import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
public class NoteViewModel extends ViewModel {
    private NoteRepository repository;
    public void init(Context context) {
        if (repository == null) {
            repository = new NoteRepository(context);
        }
    }
    public LiveData<List<NoteEntity>> getAllNotes() { return
            repository.getAllNotes(); }
    public LiveData<List<NoteEntity>> getArchivedNotes() { return
            repository.getArchivedNotes(); }
    public LiveData<List<NoteEntity>> getTrashedNotes() { return
            repository.getTrashedNotes(); }
    public LiveData<List<NoteEntity>> searchNotes(String query) { return
            repository.searchNotes(query); }
    public void insertNote(NoteEntity note) { repository.insertNote(note); }
    public void updateNote(NoteEntity note) { repository.updateNote(note); }
    public void deleteNote(NoteEntity note) { repository.deleteNote(note); }
    public void archiveNote(NoteEntity note) { repository.archiveNote(note); }
    public void trashNote(NoteEntity note) { repository.trashNote(note); }
    public void restoreNote(NoteEntity note) { repository.restoreNote(note); }
    public LiveData<List<NoteEntity>> getNotesByTopic(String topicId) {
        return repository.getNotesByTopic(topicId);
    }

    public LiveData<NoteEntity> getNoteById(String id) {
        return repository.getNoteById(id);
    }
}

