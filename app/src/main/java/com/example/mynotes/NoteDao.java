package com.example.mynotes;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes WHERE isArchived = 0 AND isTrashed = 0 AND userId = :userId ORDER BY createdAt DESC")
    LiveData<List<NoteEntity>> getAllNotes(String userId);
    @Query("SELECT * FROM notes WHERE isArchived = 1 AND isTrashed = 0 AND userId = :userId")
    LiveData<List<NoteEntity>> getArchivedNotes(String userId);
    @Query("SELECT * FROM notes WHERE isTrashed = 1 AND userId = :userId")
    LiveData<List<NoteEntity>> getTrashedNotes(String userId);
    @Query("SELECT * FROM notes WHERE userId = :userId AND isTrashed = 0 AND (title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%')")
    LiveData<List<NoteEntity>> searchNotes(String userId, String query);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteEntity note);
    @Update
    void update(NoteEntity note);
    @Delete
    void delete(NoteEntity note);
}