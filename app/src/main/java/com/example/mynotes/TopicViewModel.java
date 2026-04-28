package com.example.mynotes;
import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;public class TopicViewModel extends ViewModel {

    private TopicRepository repository;

    public void init(Context context) {
        if (repository == null) {
            repository = new TopicRepository(context);
        }
    }

    public LiveData<List<TopicEntity>> getAllTopics() {
        return repository.getAllTopics();
    }

    public void insertTopic(TopicEntity topic) {
        repository.insertTopic(topic);
    }

    public void deleteTopic(TopicEntity topic) {
        repository.deleteTopic(topic);
    }
}
