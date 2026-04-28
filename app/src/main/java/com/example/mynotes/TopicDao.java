package com.example.mynotes;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
@Dao
public interface TopicDao {
    @Query("SELECT * FROM topics WHERE userId = :userId")
    LiveData<List<TopicEntity>> getAllTopics(String userId);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TopicEntity topic);
    @Delete
    void delete(TopicEntity topic);
}